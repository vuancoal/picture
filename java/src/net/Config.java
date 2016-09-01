package net;

public interface Config {
	int PORT = 8080;
	int MAX_NUMBER_OF_CLIENT = 1;

	String HOST1 = "localhost";
	String HOST2 = "www.google.com";
	String REQUEST_SENTENCE = String.format("GET /index.html HTTP/1.0\r\nHost: %s:%d\r\n", HOST2, PORT);
}
