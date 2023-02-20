package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/calc")
public class CalculatorServlet extends HttpServlet {

    //Variable which holds the calculations performed in list for history.
    List<List<String>> history = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //show calculator page
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>SIMPLE CALCULATOR<br><br><br></head>");
        out.println("<body>");
        out.println("<form method = 'post' action = 'calc'>");
        out.println("enter the first number:<br>");
        out.println("<input type = 'text' name='number1'><br><br>");
        out.println("enter the second number:<br>");
        out.println("<input type = 'text' name='number2'><br><br>");
        out.println("enter the operation:<br><br>");
        out.println("<input type ='radio' name = 'op' value = '+'>add<br>");
        out.println("<input type = 'radio' name = 'op' value = '-'>sub<br>");
        out.println("<input type = 'radio' name = 'op' value = '*'>mul<br>");
        out.println("<input type = 'radio' name = 'op' value = '/'>div<br><br>");
        out.println("<input type = 'submit' name = 'result' value = 'submit'><br>");

        PerformSimpleCalculation(request, out);

        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    private void PerformSimpleCalculation(HttpServletRequest request, PrintWriter out) {
        try {
            String firstInput = request.getParameter("number1");
            String secondInput = request.getParameter("number2");

            Double input1 = null, input2 = null;

            if (firstInput.isEmpty()) throw new InvalidObjectException("Input 1 required");
            if (secondInput.isEmpty()) throw new InvalidObjectException("Input 2 required");

            input1 = Double.valueOf(firstInput);
            input2 = Double.valueOf(secondInput);

            String operation = request.getParameter("op");
            if (operation == null) {
                throw new InvalidObjectException("Select an operation");
            }

            Double result = null;

            result = ApplyExactOperation(input1, input2, operation, result);

            out.println("<br><br><h3> The result of " + String.format("%.0f", input1) + " " + operation + "  " + String.format("%.0f", input2) + " = " + result + "");
            history.add(new ArrayList<>(Arrays.asList(firstInput, operation, secondInput, String.valueOf(result))));

            if (history.size() > 1) {
                out.println("<br><br><table border='1px solid';text-align=center;><th border='1px solid'>First</th><th border='1px solid'>Operation</th><th border='1px solid'>Second</th><th>Result</th>");
                for (List list : history) {
                    out.println("<tr><td border='1px solid'>" + list.get(0) + "</td><td border='1px solid'>" + list.get(1) + "</td><td border='1px solid'>" + list.get(2) + "</td><td border='1px solid'>" + list.get(3) + "</td></tr>");

                }
                out.println("</table>");
            }

        } catch (Exception ex) {
            if (ex instanceof InvalidObjectException) {
                System.out.println("<br><br> Error: " + ex.getMessage());
            } else if (ex instanceof NumberFormatException) {
                System.out.println("<br><br> Error: Invalid number");
            }
        }
    }

    private Double ApplyExactOperation(Double input1, Double input2, String operation, Double result) {
        switch (operation) {
            case "+":
                result = input1 + input2;
                break;
            case "-":
                result = input1 - input2;
                break;
            case "*":
                result = input1 * input2;
                break;
            case "/":
                result = input1 / input2;
                break;
        }
        return result;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
