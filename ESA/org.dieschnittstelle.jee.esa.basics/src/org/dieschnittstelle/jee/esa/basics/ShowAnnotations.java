package org.dieschnittstelle.jee.esa.basics;


import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.dieschnittstelle.jee.esa.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.jee.esa.basics.annotations.StockItemProxyImpl;
import org.dieschnittstelle.jee.esa.basics.reflection.ReflectedStockItemBuilder;
import org.apache.log4j.Logger;

public class ShowAnnotations {
	
	protected static Logger logger = Logger
			.getLogger(ReflectedStockItemBuilder.class);

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// we initialise the collection
		StockItemCollection collection = new StockItemCollection(
				"stockitems_annotations.xml", new AnnotatedStockItemBuilder());
		// we load the contents into the collection
		collection.load();

		for (IStockItem consumable : collection.getStockItems()) {
			;
			showAttributes(((StockItemProxyImpl)consumable).getProxiedObject());
		}

		// we initialise a consumer
		Consumer consumer = new Consumer();
		// ... and let them consume
		consumer.doShopping(collection.getStockItems());
	}

	/*
	 * UE BAS2 und BAS3
	 */
	private static void showAttributes(Object consumable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<? extends Object> k = consumable.getClass();
		String name = consumable.getClass().getSimpleName().toString();
		StringBuilder attribute = new StringBuilder();
		attribute.append("{" + name + " ");
		for (Field field: k.getDeclaredFields()) {
			// Check if field has an annotation
			if(field.isAnnotationPresent(DisplayAs.class)) {
				DisplayAs da = field.getAnnotation(DisplayAs.class);	
				attribute.append(da.value());
			} else {
				// if field has no annotation, use the given name of the field
				attribute.append(field.getName().toString());
			}
			String fieldname = field.getName();
			fieldname = "get" + fieldname.substring(0,1).toUpperCase() + fieldname.substring(1);
			Method m = k.getDeclaredMethod(fieldname);
			attribute.append(": ");
			// read data out of field
			attribute.append(m.invoke(consumable));
			attribute.append(", ");
		}

		// output without obsolete comma and space
		logger.debug(attribute.toString().substring(0,attribute.toString().length()-2) + "}");
	}

}
