package entity;

import entity.Customer;
import entity.OrderedProduct;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-01-04T23:26:39")
@StaticMetamodel(CustomerOrder.class)
public class CustomerOrder_ { 

    public static volatile SingularAttribute<CustomerOrder, BigDecimal> amount;
    public static volatile SingularAttribute<CustomerOrder, Date> dateCreated;
    public static volatile SingularAttribute<CustomerOrder, Integer> confirmationNumber;
    public static volatile SingularAttribute<CustomerOrder, String> customerOrdercol;
    public static volatile SingularAttribute<CustomerOrder, Customer> customerid;
    public static volatile SingularAttribute<CustomerOrder, Integer> id;
    public static volatile CollectionAttribute<CustomerOrder, OrderedProduct> orderedProductCollection;

}