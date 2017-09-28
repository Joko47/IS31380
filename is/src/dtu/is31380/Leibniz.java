package dtu.is31380;

public class Leibniz {
	public static double pi(int n) {
	    double sum = 0;
	    for (int i=0; i < n; i++) {
	        sum += Math.pow(-1, i)/(2.0 * i + 1);
	    }
	    return 4.0 * sum;
	}
}
