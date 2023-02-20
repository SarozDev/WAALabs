package mvc;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.dnd.InvalidDnDOperationException;
import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public ModelAndView hello(@RequestParam(value = "firstname") String firstName,
                              @RequestParam(value = "lastname") String lastName) {

        String message = firstName + " " + lastName;

        Map<String, Object> params = new HashMap<>();
        params.put("message", message);

        return new ModelAndView("hello", params);
    }

    @RequestMapping("/calc")
    public ModelAndView calc(@RequestParam(value = "num1") Double num1, @RequestParam(value = "num2") Double num2, @RequestParam(value = "op") String op) {

        Map<String, Object> params = new HashMap<>();
        try {
            Double result = null;

            switch (op) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                default:
                    throw new InvalidDnDOperationException("Invalid operation!");
            }

            params.put("number1", num1);
            params.put("op", op);
            params.put("number2", num2);
            params.put("result", result);

        } catch (Exception ex) {
            if (ex instanceof NumberFormatException) {
                params.put("error", "Invalid input");
            }
        }

        return new ModelAndView("calc", params);
    }


}

