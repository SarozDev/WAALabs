package edu.miu.lab2shoppingcart.controller;

import edu.miu.lab2shoppingcart.model.Cart;
import edu.miu.lab2shoppingcart.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

    private Map<String, Product> products;
    private Map<String, Cart> shoppingCart;
    private int myCart;

    private ProductController() {

    }

    @GetMapping("/products")
    public ModelAndView getProducts(HttpSession session) {
        products = (Map<String, Product>) session.getAttribute("products");
        if (products == null) {
            products = new HashMap<>();
            session.setAttribute("products", products);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("products", products.values());
        return new ModelAndView("products", params);
    }

    @PostMapping("/addProduct")
    public ModelAndView addProduct(HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        params.put("product", new Product());
        return new ModelAndView("addProduct", params);
    }

    @PostMapping("/add")
    public ModelAndView add(HttpSession session, @Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("addProduct");
        }

        Map<String, Product> productMap = getProductListFromSession(session);
        productMap.put(product.getNumber(), product);
        session.setAttribute("products", productMap);
        return new ModelAndView("redirect:/products");
    }

    private Map<String, Product> getProductListFromSession(HttpSession session) {
        Map<String, Product> productMap = (Map<String, Product>) session.getAttribute("products");
        if (productMap == null) {
            productMap = new HashMap<>();
        }
        return productMap;
    }

    @PostMapping("/removeProduct")
    public ModelAndView remove(@RequestParam("number") String number, HttpSession session) {
        Map<String, Object> params = new HashMap<>();

        if (number != null) {
            products = (Map<String, Product>) session.getAttribute("products");
            if (products == null) {
                products = new HashMap<>();
                session.setAttribute("products", products);
            }

            products.remove(number);
            params.put("products", products.values());
        }

        return new ModelAndView("products", params);
    }

    @PostMapping("/addProductCart")
    public ModelAndView addProductCart(@RequestParam("number") String number, HttpSession session) {
        Map<String, Object> params = new HashMap<>();

        if (number != null) {
            shoppingCart = (Map<String, Cart>) session.getAttribute("shoppingCart");
            if (shoppingCart == null) {
                shoppingCart = new HashMap<>();
                session.setAttribute("shoppingCart", shoppingCart);
            }
            //setShoppingCart(session, "myCart");

            if (shoppingCart.containsKey(number)) {
                Cart cart = shoppingCart.get(number);
                cart.setQuantity(cart.getQuantity() + 1);
                shoppingCart.put(number, cart);
            } else {
                Product product = products.get(number);
                if (product != null) {
                    shoppingCart.put(number, new Cart(1, product));
                }
            }
            params.put("shoppingCart", shoppingCart.values());
        }

        return new ModelAndView("redirect:showCart", params);
    }

    private void setShoppingCart(HttpSession session, String myCart) {
        shoppingCart = (Map<String, Cart>) session.getAttribute("shoppingCart");
        if (shoppingCart == null) {
            shoppingCart = new HashMap<>();
            session.setAttribute("shoppingCart", shoppingCart);
        }
    }

    @GetMapping("/showCart")
    public ModelAndView showCart(HttpSession session) {
        setShoppingCart(session, "shoppingCart");

        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCart", shoppingCart.values());
        params.put("myCart", shoppingCart.values().stream().mapToDouble(Cart::getTotalPrice).sum());
        return new ModelAndView("cart", params);
    }

    @PostMapping("/removeProductCart")
    public ModelAndView removeProductCart(@RequestParam("number") String number, HttpSession session) {
        Map<String, Object> params = new HashMap<>();

        if (number != null) {
            shoppingCart = (Map<String, Cart>) session.getAttribute("shoppingCart");
            if (shoppingCart == null) {
                shoppingCart = new HashMap<>();
                session.setAttribute("shoppingCart", shoppingCart);
            }
            //setShoppingCart(session, "shoppingCart");

            Cart item = shoppingCart.get(number);

            if (item.getQuantity() == 1) {
                shoppingCart.remove(number);
            } else {
                item.setQuantity(item.getQuantity() - 1);
                shoppingCart.put(number, item);
            }

            params.put("shoppingCart", shoppingCart.values());
        }
        return new ModelAndView("redirect:showCart", params);
    }
}
