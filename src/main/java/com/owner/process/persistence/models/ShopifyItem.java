package com.owner.process.persistence.models;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity @Table(name="##plant##SHOPIFY_ITEM") 
@Getter @Setter
public class ShopifyItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name="VARIANT_ID")
	private String variantId;
	@Column(name="PRODUCT_ID")
	private String productId;
	@Column(name="SKU")
	private String sku;
	@Column(name="INVENTORY_ID")
	private String inventoryId;
	@Column(name="PLANT")
	private String plant;
	@Column(name="CRAT")
	private String crAt;
	@Column(name="CRBY")
	private String crBy;
	@Column(name="UPAT")
	private String upAt;
	@Column(name="UPBY")
	private String upBy;
}