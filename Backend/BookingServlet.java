import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private BookingDAO bookingDAO = new BookingDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        BookingRequest bookingRequest = gson.fromJson(sb.toString(), BookingRequest.class);

        JsonObject jsonResponse = new JsonObject();
        if (bookingRequest == null || bookingRequest.movieTitle == null || bookingRequest.seats == null
                || bookingRequest.name == null || bookingRequest.email == null) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Invalid booking data");
            resp.getWriter().write(jsonResponse.toString());
            return;
        }

        try {
            int totalAmount = Integer.parseInt(bookingRequest.total);
            boolean saved = bookingDAO.saveBooking(bookingRequest.movieTitle, bookingRequest.seats,
                    totalAmount, bookingRequest.name, bookingRequest.email);
            jsonResponse.addProperty("success", saved);
            jsonResponse.addProperty("message", saved ? "Booking successful" : "Booking failed");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Internal error");
        }
        resp.getWriter().write(jsonResponse.toString());
    }

    private static class BookingRequest {
        String movieTitle;
        String seats;
        String total;
        String name;
        String email;
    }
}
