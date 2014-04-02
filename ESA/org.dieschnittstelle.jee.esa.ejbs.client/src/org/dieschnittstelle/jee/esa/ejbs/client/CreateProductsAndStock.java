package org.dieschnittstelle.jee.esa.ejbs.client;

public class CreateProductsAndStock {

	public static void main(String[] args) {
		
		CreateProducts.main(new String[]{});
		
		Util.step();

		CreateStock.main(new String[]{});

	}
	
}
