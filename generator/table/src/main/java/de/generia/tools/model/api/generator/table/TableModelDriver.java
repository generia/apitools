package de.generia.tools.model.api.generator.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.generia.tools.dom2table.marshaller.simple.SimpleModelDriver;
import de.generia.tools.dom2table.model.ObjectDriver;
import de.generia.tools.dom2table.model.tree.TreeDriver;
import de.generia.tools.model.api.EClassifier;

public class TableModelDriver extends SimpleModelDriver<EClassifier> {
	//private static final Logger LOG = LoggerFactory.getLogger(TableModelDriver.class);

	private TreeDriver treeDriver;
	private TableTreeBuilder treeBuilder;

	public TableModelDriver(EClassifier databaseType, Map<String, EClassifier> rowTypeMap, ObjectDriver<EClassifier> objectDriver, TreeDriver treeDriver, TableTreeBuilder treeBuilder) {
		super(databaseType, rowTypeMap, objectDriver);
		this.treeDriver = treeDriver;
		this.treeBuilder = treeBuilder;
	}

	@Override
	protected Object createTableObject(Object databaseObject, String tableProperty) {
		treeBuilder.contextNode = databaseObject;
		return databaseObject;
	}

	public Iterable<Object> getRowObjects(Object databaseObject, String tableProperty) {
		List<Object> objects = new ArrayList<Object>();
		collectObjects(databaseObject, objects);
		System.out.println("TableModelDriver.getRowObjects: collected row-objects #" + objects.size() + " ...");
		return objects;
	}

	private void collectObjects(Object object, List<Object> objects) {
		//LOG.info("collectObjects: object='{}'", object);
		objects.add(object);
		for (Object child : treeDriver.getChildren(object)) {
			collectObjects(child, objects);
		}
	};
}