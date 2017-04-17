package de.generia.tools.model.api.trafo.dot;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;

public class Api2DotTrafo {
	private Map<EClassifier, Node> nodes;
	private Map<ETypedElement, Edge> edges;
	private int nodeCount;
	private Stack<String> packageStack = new Stack<>();
	
	public Api2DotTrafo() {

	}
	
	public void write(EPackage pkg, PrintWriter writer) {
		nodes = new LinkedHashMap<>();
		edges = new LinkedHashMap<>();		
		visitElement(pkg);
		
		writeGraphBeg(writer);
		for (Node node : nodes.values()) {
			writeNode(writer, node, "\t");
		}
		for (Map.Entry<ETypedElement, Edge> entry : edges.entrySet()) {
			writeEdge(writer, entry.getKey(), entry.getValue(), "\t");
		}
		writeGraphEnd(writer);
		writer.flush();
	}

	private void writeGraphBeg(PrintWriter writer) {
		writer.println("digraph G {");
		writer.println("\tedge [fontname=\"arialbd\",fontsize=10,labelfontname=\"arialbd\",labelfontsize=10];");
		writer.println("\tnode [fontname=\"arial\",fontsize=10,shape=plaintext];");
		writer.println("\tnodesep=0.25;");
		writer.println("\tranksep=0.5;");
	}

	private void writeGraphEnd(PrintWriter writer) {
		writer.println("}");
	}

	private void writeNode(PrintWriter writer, Node node, String ind) {
		if (!(node.classifier instanceof EClass)) {
			return;
		}
		EClass clazz = (EClass) node.classifier;
		String title = node.classifier.getName();
		String className = node.classifier.getName();
		String packageName = node.packageName;
		String stereotypes = toHtml(getStereotypes(node.classifier));
		
		writer.println();
		writer.println(ind + "// " + node.getQName());

		writer.println(ind + node.id + " [label=<<table title=\"" + title + "\" border=\"0\" cellborder=\"1\" cellspacing=\"0\" cellpadding=\"2\" port=\"p\" color=\"#FFD000\" gradientangle=\"90\" bgcolor=\"#FFFF00;0:#FFFFA0\">");
		
		// class title
		writer.println(ind + "<tr><td><table border=\"0\" cellspacing=\"0\" cellpadding=\"1\">");
		if (stereotypes != null && !stereotypes.isEmpty()) {
			writer.println(ind + "<tr><td align=\"center\" balign=\"center\"><font point-size=\"8.0\"> " + stereotypes + " </font></td></tr>");
		}
		writer.println(ind + "<tr><td align=\"center\" balign=\"center\"><font face=\"arial\"><b> " + className + " </b></font></td></tr>");
		if (packageName != null && !packageName.isEmpty()) {
			writer.println(ind + "<tr><td align=\"center\" balign=\"center\"><font point-size=\"8.0\"> " + packageName + " </font></td></tr>");
		}
		writer.println(ind + "</table></td></tr>");

		// attributes
		if (!clazz.getStructuralFeatures().isEmpty()) {
			writer.println(ind + "<tr><td><table border=\"0\" cellspacing=\"0\" cellpadding=\"1\">");
			for (EStructuralFeature feature : clazz.getStructuralFeatures()) {
				String spec = getTypedElement(feature);
				if (feature instanceof EAttribute && ((EAttribute)feature).isId()) {
					spec = "<b>&lt;&lt;id&gt;&gt; " + spec + "</b>";
				}
				writer.println(ind + "<tr><td align=\"left\" balign=\"left\"> " + spec + " </td></tr>");
			}
			writer.println(ind + "</table></td></tr>");
		}
		
		// operations
		if (!clazz.getOperations().isEmpty()) {
			writer.println(ind + "<tr><td><table border=\"0\" cellspacing=\"0\" cellpadding=\"1\">");
			for (EOperation operation : clazz.getOperations()) {
				String spec = operation.getName() + "(";
				String sep = "";
				for (EParameter parameter : operation.getParameters()) {
					String paramSpec = getType(parameter);
					spec += sep + paramSpec;
					sep = ",";
				}
				spec +=  ") : " + getType(operation);
				writer.println(ind + "<tr><td align=\"left\" balign=\"left\"> " + spec + " </td></tr>");
			}		
			writer.println(ind + "</table></td></tr>");
		}
		writer.println(ind + "</table>> fontname=\"arial\" fontcolor=\"#404040\" fontsize=9.0];");
	}

	private void writeEdge(PrintWriter writer, ETypedElement typedElement, Edge edge, String ind) {
		Node source = nodes.get(edge.sourceType);
		Node target = nodes.get(edge.targetType);
		if (source == null || target == null) {
			return;
		}
		Edge.Type edgeType = edge.edgeType;
		
		writer.println(ind + "// " + edgeType + ": " + source.getQName() + " " + edgeType + " " + target.getQName());
		writer.print(ind + source.id + ":p -> " + target.id + ":p");
		switch (edgeType) {
		case inheritance:
			writer.println(" [weight=\"1000\" arrowhead=\"onormal\"];");
			break;
		case usage:
			writer.println(" [weight=\"0\" style=\"dotted\" arrowhead=\"open\"];");
			break;
		case association:
			EReference reference = (EReference) typedElement;
			String label = typedElement.getName();
			String taillabel = "";
			String headlabel = typedElement.isMany() ? "*" : "";
			if (!reference.isContainment() && reference.getOpposite() != null) {
				label = "";
				taillabel = reference.getOpposite().getName() + (reference.getOpposite().isMany() ? "*" : "");
				headlabel = reference.getName() + headlabel;
			}
			String arrowhead = "none";
			String arrowtail = reference.isContainment() ? "diamond" : reference.getOpposite() != null ? "odiamond" : "odiamond";
			String dir = reference.isContainment() ? "both" : reference.getOpposite() != null ? "both" : "both";
			String weight = reference.isContainment() ? "100" : reference.getOpposite() != null ? "0" : "0";
			writer.println(" [label=\"" + label + "\" taillabel=\"" + taillabel + "\" headlabel=\"" + headlabel + "\" arrowtail=\"" + arrowtail + "\" arrowhead=\""+ arrowhead + "\" dir=\"" + dir + "\" weight=\"" + weight + "\" fontname=\"arial\" fontcolor=\"blue\" fontsize=10.0 color=\"#4040A0\"];");

		default:
			break;
		}
	}

	private String toHtml(String string) {
		String html = string.replace("&", "&amp;");
		html = html.replace("\"", "&quot;");
		html = html.replace("<", "&lt;");
		html = html.replace(">", "&gt;");
		return html;
	}

	private String getStereotypes(EModelElement element) {
		StringBuilder stereotypes = new StringBuilder();
		String sep = "";
		for (EAnnotation annotation : element.getAnnotations()) {
			stereotypes.append(sep).append("<<").append(annotation.getSource()).append(">>");
			sep = " ";
		}
		return stereotypes.toString();
	}

	private String getTypedElement(ETypedElement element) {
		return element.getName() + " : " + getType(element);
	}

	private String getType(ETypedElement typedElement) {
		String type = typedElement.getType() != null ? typedElement.getType().getName() : "?";
		if (typedElement.isMany()) {
			String collectionType = typedElement.isOrdered() ? "List" : "Set";
			type = collectionType + "&lt;" + type + "&gt;";
		}
		return type;
	}

	private void visitElement(EModelElement element) {
		if (element instanceof EPackage) {
			visitPackage((EPackage)element);
		} else if (element instanceof EClass) {
			visitClass((EClass)element);
		}
	}

	private void visitPackage(EPackage element) {
		packageStack.push(element.getName());
		for (EClassifier classifier : element.getClassifiers()) {
			visitElement(classifier);
		}
		for (EPackage pkg : element.getSubPackages()) {
			visitElement(pkg);
		}
		packageStack.pop();
	}

	private void visitClass(EClass element) {
		String packageName = getPackageName();
		Node node = new Node(packageName, element);
		nodes.put(element, node);
		if (element.getSuperType() != null) {
			Edge edge = new Edge(Edge.Type.inheritance, element, element.getSuperType());
			edges.put(new EReference(), edge);
		}
		for (EStructuralFeature feature : element.getStructuralFeatures()) {
			if (feature instanceof EReference) {
				EReference reference = (EReference) feature;
				if (reference.getOpposite() != null) {
					if (edges.containsKey(reference.getOpposite())) {
						continue;
					}
					// make sure containment-side is used as edge source
					if (!reference.isContainment() && reference.getOpposite().isContainment()) {
						continue;
					}
				}
				Edge edge = new Edge(Edge.Type.association, element, feature.getType());
				edge.reference = (EReference) feature;
				edges.put(edge.reference, edge);
			}
		}
		Set<EClass> usageTargets = new HashSet<>();
		for (EOperation operation : element.getOperations()) {
			addUsageEdge(element, operation, usageTargets);
			for (EParameter parameter : operation.getParameters()) {
				addUsageEdge(element, parameter, usageTargets);
			}
		}
		for (EClassifier classifier : element.getNestedClassifiers()) {
			visitElement(classifier);
		}
	}
	
	private void addUsageEdge(EClass element, ETypedElement typedElement, Set<EClass> usageTargets) {
		EClassifier classifier = typedElement.getType();
		if (!(classifier instanceof EClass)) {
			return;
		}
		EClass type = (EClass) classifier;
		if (usageTargets.contains(type)) {
			return;
		}
		Edge edge = new Edge(Edge.Type.usage, element, type);
		edges.put(edge.reference, edge);
	}

	private String getPackageName() {
		String sep = "";
		StringBuilder packageName = new StringBuilder();
		for (String name : packageStack) {
			packageName.append(sep).append(name);
			sep = ".";
		}
		return packageName.toString();
	}

	private class Node {		
		String id;
		String packageName;
		EClassifier classifier;

		public Node(String packageName, EClassifier classifier) {
			this.packageName = packageName;
			this.classifier = classifier;
			this.id = "n" + (nodeCount++);
		}
		
		public String getQName() {
			return packageName + "." + classifier.getName();
		}
	}
	
	private static class Edge {
		Type edgeType;
		EClassifier sourceType;
		EClassifier targetType;
		EReference reference;
		public Edge(Type edgeType, EClassifier sourceType, EClassifier targetType) {
			this.edgeType = edgeType;
			this.sourceType = sourceType;
			this.targetType = targetType;
		}
		public enum Type { inheritance, association, usage };
	}
 }
