package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SHOPIFY_CONFIG")
@Getter
@Setter
public class ShopifyConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "PLANT")
    private String plant;
    @Column(name = "API_KEY")
    private String apiKey;
    @Column(name = "API_PASSWORD")
    private String apiPassword;
    @Column(name = "DOMAIN_NAME")
    private String domainName;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "WEBHOOK_KEY")
    private String webhookKey;
    @Column(name = "CRAT")
    private String crAt;
    @Column(name = "CRBY")
    private String crBy;
    @Column(name = "UPAT")
    private String upAt;
    @Column(name = "UPBY")
    private String upBy;
}