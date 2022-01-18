import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class is a helper class for calculating statistics based on a list of
 * integer values. In both Question 1 and Question 2, you will need to calculate
 * the min, max, mean, Q1, Q2, and Q3 of a list of integers, as well as count
 * the frequency of each integer.
 *
 * IMPORTANT: This class is not yet complete, so must be completed by you.
 *
 * To accomplish this, you should fill in the missing parts of this code.
 * Project 3 builds upon Projects 1 and 2 here! Finding the top N foods by
 * calorie count is similar to finding the first second and third quartiles. I
 * recommend you consider using a min heap (via the PriorityQueue class) to
 * complete this code. However, you may use whatever means you prefer (sorting,
 * your solution to Project 1, etc.).
 *
 * @author Mark Hancock
 * @author Johayer Chowdury
 *
 *
 *         Johayer Rahman Chowdury & Mark Hancock 
 *         Final Project 
 *         December 15/ 2020 
 *         Class template for statistics object, used to compute
 *         statistics. Formulas were taken from MSCI 251. 
 *         Description of Input:A list of integers to be sorted. 
 *         Description of Output: Statistics
 */
public class Statistics {
  // use TreeMap to find counts
  private HashMap < Integer, Integer > counts = new HashMap < >();

  // use priority queue min heap to find minimum
  private PriorityQueue < Integer > minToMax = new PriorityQueue < Integer > ();

  // use priority queue max heap to find maximum
  private PriorityQueue < Integer > maxToMin = new PriorityQueue < Integer > (Collections.reverseOrder());

  private int min;
  private int max;
  private double mean;
  private double q1;
  private double q2;
  private double q3;

  /**
	 * Constructs a new Statistics object based on a list of integers. This
	 * class doesn't store the integers, but instead calculates the min, max,
	 * Q1-Q3, and counts the number of occurrences of each value.
	 *
	 * @param list the list of integers to collect statistics from.
	 */
  public Statistics(List < Integer > list) {
    calculateFromList(list);
  }

  /**
	 * This method calculates the min, max, mean, Q1, Q2, and Q3 values from a
	 * list of integers, and also counts the frequency (number of occurrences)
	 * of each repeated value in the list.
	 *
	 * @param list a list of unsorted integers, with possible repeated values.
	 */
  private void calculateFromList(List < Integer > list) {
    /*
		 * By the end of this method, you should have calculated the min, max,
		 * mean, Q1, Q2, and Q3 values, as well as populated the counts HashMap
		 * with the counts of each value.
		 */

    // used in calculation of mean; sum of all the integers
    double sum = 0;

    for (int number: list) {

      // add elements to priority queue
      maxToMin.add(number);

      // integers that are less than or equal to 0 should not be added to
      // the total
      // this is to ensure that the mean and quartiles do not include
      // distances of -1 or 0
      if (number > 0) {
        sum += number;
        minToMax.add(number);
      }

      // if key found in map, increment count
      if (counts.containsKey(number)) {
        counts.put(number, counts.get(number) + 1);
      }
      // otherwise, add key into map with count of 1
      else {
        counts.put(number, 1);
      }

    }

    // minimum
    min = minToMax.peek();
    // maximum
    max = maxToMin.peek();

    // mean
    int sizeOfDataSet = minToMax.size();
    mean = sum / sizeOfDataSet;

    // quartiles found using private method
    q1 = percentile(25, sizeOfDataSet, minToMax);
    q2 = percentile(50, sizeOfDataSet, minToMax);
    q3 = percentile(75, sizeOfDataSet, minToMax);

  }

  /**
	 * 
	 * @param P:             the given percentile
	 * @param sizeOfDataSet: the size of the priority queue with only integers
	 *                       greater than zero
	 * @param pq:            the priority queue with only integers greater than
	 *                       zero
	 * @return the quartile
	 */
  // private method to find the percentile with the sizeOfDataSet excluding
  // integers equal to or less than zero
  // *formulas to calculate percentile were taken from MSCI 251
  private double percentile(double P, int sizeOfDataSet, PriorityQueue < Integer > pq) {

    // initialize queue to hold removed elements from priority queue
    Queue < Integer > queue = new LinkedList < Integer > ();

    // initialize quartile
    double quartile;
    
    // *find index
    double index = (P * sizeOfDataSet) / 100;
    
    // *if index calculated is a whole number
    if (index % 1 == 0) {
      int index1 = (int) index;
      // assign values to the values in priority queue
      int value1 = forLoopForPQ(index1, pq, queue);
      int value2 = pq.remove();
//      queue.add(value2);
      
      // *the average between the two values found is the quartile
      quartile = 0.5 * (value1 + value2);
    }
    // *if index calculated is NOT a whole number
    else {
      int index1 = (int) Math.floor(index + 1);
      // assign value to the value in priority queue
      quartile = forLoopForPQ(index1, pq, queue);
    }

    // add elements in queue back into the priority queue
//    while (!queue.isEmpty()) {
//      pq.add(queue.remove());
//    }

    return quartile;
  }

  /**
	 * 
	 * @param index: given index found in percentile method
	 * @param pq:    priority queue used to hold data set
	 * @param queue: queue to hold removed elements from pq
	 * @return: the value found at the given index in pq
	 */
  // private method used to iterate through priority queue
  private int forLoopForPQ(int index, PriorityQueue < Integer > pq, Queue < Integer > queue) {
    // for indexes less than the index we are looking for,
    // remove values from priority queue and add to the queue
    for (int i = 1; i < index; i++) {
      queue.add(pq.remove());
    }

    // the value removed is at the index we are looking for
    int value = pq.remove();
    // add it to the queue
    queue.add(value);

    return value;
  }

  /**
	 * Returns a list of ordered unique values based on the original list with
	 * duplicates.
	 *
	 * @return a list of ordered unique values based on the original list with
	 *         duplicates
	 */
  public List < Integer > getSortedUniqueKeys() {
    ArrayList < Integer > keys = new ArrayList < >(counts.keySet());
    Collections.sort(keys);
    return keys;
  }

  /**
	 * Returns the number of occurrences of a specific value.
	 *
	 * @param value the value to check for occurrences
	 * @return the number of occurrences
	 */
  public int getCountOf(int value) {
    if (counts.get(value) == null) {
      return 0;
    }
    return counts.get(value);
  }

  /**
	 * Returns the minimum value from the list.
	 *
	 * @return the minimum value from the list
	 */
  public int getMin() {
    return min;
  }

  /**
	 * Returns the maximum value from the list.
	 *
	 * @return the maximum value from the list
	 */
  public int getMax() {
    return max;
  }

  /**
	 * Returns the mean value from the list.
	 *
	 * @return the mean value from the list
	 */
  public double getMean() {
    return mean;
  }

  /**
	 * Returns the first quartile value from the list.
	 *
	 * @return the first quartile value from the list
	 */
  public double getQ1() {
    return q1;
  }

  /**
	 * Returns the second quartile (a.k.a. median) value from the list.
	 *
	 * @return the first quartile value from the list
	 */
  public double getQ2() {
    return q2;
  }

  /**
	 * Returns the third quartile value from the list.
	 *
	 * @return the third quartile value from the list
	 */
  public double getQ3() {
    return q3;
  }
}