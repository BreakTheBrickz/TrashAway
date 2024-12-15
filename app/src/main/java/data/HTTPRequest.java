package data;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;


public class HTTPRequest {

        private static final OkHttpClient CLIENT = new OkHttpClient();

        public static Request.Builder createPostRequest(String endpoint, List<Pair<String, String>> headers, List<Pair<String, String>> formData) {
            String url = "http://84.252.122.59/" + endpoint;

            // Build the form body
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Pair<String, String> data : formData) {
                formBuilder.add(data.getFirst(), data.getSecond());
            }
            FormBody formBody = formBuilder.build();

            // Create a POST request with form data in the body
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .post(formBody);  // Use POST instead of GET

            // Add headers
            for (Pair<String, String> header : headers) {
                builder.addHeader(header.getFirst(), header.getSecond());
            }
            return builder;
        }

        public static String sendRequest(Request request) {
            try (Response response = CLIENT.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().string();
                }
                throw new IOException("Unexpected code " + response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static void main(String[] args) {
            // Create form data
            List<Pair<String, String>> formData = List.of(
                    new Pair<>("name", "Test Location"),
                    new Pair<>("latitude", "45.6789"),
                    new Pair<>("longitude", "123.4567"),
                    new Pair<>("icon_description", "Tree")
            );

            // Create the POST request with form data
            Request request = createPostRequest("add_location.php", List.of(), formData).build();

            // Send the request and print the response
            String response = sendRequest(request);
            System.out.println(response);
        }


}
