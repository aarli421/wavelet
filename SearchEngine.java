import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> queries = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().contains("/search")) {
            StringBuilder builder = new StringBuilder();
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for (String query : queries) {
                    if (query.indexOf(parameters[1]) != -1) {
                        builder.append(query + " ");
                    }
                }
            }
            return builder.toString();
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                queries.add(parameters[1]);
            }
            return String.format("%s %s", "Added", parameters[1]);
        } else {
            System.out.println("Path: " + url.getPath());
            
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
