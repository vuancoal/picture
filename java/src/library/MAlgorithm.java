package library;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.in;
import static java.lang.System.out;

public class MAlgorithm {
	private static MAlgorithm ins;
	private MIO io = MIO.getIns();

	private MAlgorithm() {
	}

	void test() {
	}

	synchronized static MAlgorithm getIns() {
		return (ins == null) ? (ins = new MAlgorithm()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public double calculate_polynomial(double a[], double x) {
		if (a == null)
			throw new RuntimeException("array == null");
		double val = a[0];
		for (int i = 1; i < a.length; ++i) {
			val = val * x + a[i];
		}
		return val;
	}

	/**
	 * calculate root-square value of non-negative double variable
	 */

	public double sqrt(double a) {
		if (a < 0.0)
			throw new RuntimeException("a must be at least 0.0");
		if (a == 0.0)
			return a;
		double un1 = a, un;
		final double eps = get_epsilon();
		do {
			un = un1;
			un1 = 0.5 * (un + a / un);
		} while (Math.abs(un1 - un) > eps);
		return un1;
	}

	public double get_epsilon() {
		double x = 1.0, eps = 1.0;
		while (x + eps != x) {
			eps = eps * 0.5;
		}
		return eps;
	}

	public <T> void insertion_sort(T[] a, int left, int right, Comparator<T> cmp) {
		if (a == null)
			throw new RuntimeException("arr must be not null");
		if (left < 0 || right >= a.length || left > right)
			throw new RuntimeException("invalid index");
		for (int i = left + 1; i <= right; ++i) {
			T key = a[i];
			int j = i - 1;
			while (j >= left && cmp.compare(key, a[j]) < 0) {
				a[j + 1] = a[j];
				--j;
			}
			a[j + 1] = key;
		}
	}

	public <T> void heap_sort(T[] arr, int left, int right, Comparator<T> cmp) {
		T tmp;
		for (int i = right; i >= left; --i) {
			build_heap(arr, left, i, cmp);
			tmp = arr[i];
			arr[i] = arr[left];
			arr[left] = tmp;
		}
	}

	private <T> void build_heap(T[] arr, int left, int right, Comparator<T> cmp) {
		for (int i = right / 2 - 1; i >= 0; --i) {
			heapify(arr, i, left, right, cmp);
		}
	}

	private <T> void heapify(T[] arr, int i, int left, int right, Comparator<T> cmp) {
		int l = (i << 1) + 1, r = (i << 1) + 2, best = i;
		T tmp = null;
		if (l <= right && cmp.compare(arr[best], arr[l]) < 0) {
			best = l;
		}

		if (r <= right && cmp.compare(arr[best], arr[r]) < 0) {
			best = r;
		}

		if (best != i) {
			tmp = arr[best];
			arr[best] = arr[i];
			arr[i] = tmp;
			heapify(arr, left, right, best, cmp);
		}
	}

	public <T> void quick_sort(T[] a, int left, int right, Comparator<T> cmp) {
		if (a == null)
			throw new RuntimeException("a must be not null !");
		if (left < 0 || right >= a.length)
			throw new RuntimeException("invalid left or right");
		if (left >= right)
			return;
		int center = quick_sort_partition(a, left, right, cmp);
		quick_sort(a, left, center - 1, cmp);
		quick_sort(a, center + 1, right, cmp);
	}

	private <T> int quick_sort_partition(T[] a, int left, int right, Comparator<T> cmp) {
		int i = left, j = right, mid = (left + right) >> 1;
		T key = a[mid];
		T tmp = a[mid];
		a[mid] = a[left];
		a[left] = tmp;
		i++;
		while (i <= j) {
			int v = cmp.compare(a[i], key);
			if (v <= 0) {
				++i;
			} else {
				tmp = a[i];
				a[i] = a[j];
				a[j] = tmp;
				--j;
			}
		}
		i--;
		tmp = a[i];
		a[i] = a[left];
		a[left] = tmp;
		return i;
	}

	public <T> void quick_sort1(T[] a, int left, int right, Comparator<T> cmp) {
		if (a == null)
			throw new RuntimeException("a must be not null !");
		if (left < 0 || right >= a.length)
			throw new RuntimeException("invalid left or right");
		if (left >= right)
			return;
		int[] center = quick_sort_partition1(a, left, right, cmp);
		quick_sort(a, left, center[0] - 1, cmp);
		quick_sort(a, center[0] + 1, right, cmp);
	}

	private <T> int[] quick_sort_partition1(T[] a, int left, int right, Comparator<T> cmp) {
		int i = left;
		// T key = a[(left + right) >> 1];
		int lt = left, gt = right;

		while (i <= gt) {
			int v = cmp.compare(a[i], a[lt]);
			if (v < 0) {
				T tmp = a[i];
				a[i] = a[lt];
				a[lt] = tmp;
				++i;
				++lt;
			} else if (v > 0) {
				T tmp = a[i];
				a[i] = a[gt];
				a[gt] = tmp;
				--gt;
			} else {
				++i;
			}
		}
		return new int[]{lt, gt};
	}

	public double consume_time(List<Double>[] units, int N) {
		int len = units.length, num = 0;
		int[] cnt = new int[len];
		double res = 0.0;
		double[] time = new double[len];
		for (int i = 0; i < len; ++i) {
			time[i] = units[i].get(0);
		}

		for (List<Double> unit : units) {
			io.println(unit);
		}
		io.println("");

		for (; num < N; ) {
			int min = min_of(time);
			io.println("min index: %d, time of it: %.2f", min, time[min]);
			res += time[min];
			++num;
			++cnt[min];
			time[min] += units[min].get(cnt[min]);
		}

		io.println("num: %d, time: %.2f", num, res);

		return res;
	}

	private int min_of(double[] time) {
		int index = -1;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < time.length; ++i) {
			if (time[i] < min) {
				min = time[i];
				index = i;
			}
		}
		return index;
	}

	public Matcher get_matcher_for_pattern(String pattern, String input, int flags) {
		Pattern p = Pattern.compile(pattern, flags);
		return p.matcher(input);
	}

	/**
	 * @param input   the string that be splitted
	 * @param pattern the regex string that be used to divide input into small strings
	 */
	public String[] pattern_split(String input, String pattern) {
		return input.split(pattern);
	}

	public static void main(String... args) {
		getIns().test();
	}
}
