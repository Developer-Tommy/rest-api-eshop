package sk.stuba.fei.uim.oop.assignment3.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int amount;
    private String unit;
    private double price;
}
