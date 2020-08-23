package decorator;

import decorator.html.AHtml;
import decorator.html.TdHtml;
import decorator.html.TrHtml;
import model.Product;
import utils.JaxbUtils;

import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.List;

public class ProductRowDecorator {

    public static String createProductHtmlRow(Product product) {
        AHtml aEdit = new AHtml("Edit", "editProduct?code=__CODE__");
        AHtml aDelete = new AHtml("Delete", "deleteProduct?code=__CODE__");
        TdHtml tdEdit = TdHtml.builder()
                .a(aEdit)
                .build();
        TdHtml tdDelete = TdHtml.builder()
                .a(aDelete)
                .build();
        TdHtml tdCode = TdHtml.builder()
                .p("__CODE__")
                .build();
        TdHtml tdName = TdHtml.builder()
                .p("__NAME__")
                .build();
        TdHtml tdPrice = TdHtml.builder()
                .p("__PRICE__")
                .build();
        List<TdHtml> tdHtmlList = Arrays.asList(tdCode, tdName, tdPrice, tdEdit, tdDelete);
        TrHtml trHtml = new TrHtml(tdHtmlList);
        String result = null;
        try {
            result = JaxbUtils.xmlToString(trHtml);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Decorator exception!");
        }
        String code = product.getCode();
        String name = product.getName();
        String price = String.valueOf(product.getPrice());
        result = result.replace("__CODE__",code);
        result = result.replace("__NAME__",name);
        result = result.replace("__PRICE__",price);
        String.format(result, code, name, price, code, code);
        return result;
    }

}
