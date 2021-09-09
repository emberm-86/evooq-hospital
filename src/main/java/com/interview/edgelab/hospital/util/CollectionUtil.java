package com.interview.edgelab.hospital.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class contains only one method to get the powerset from the input.
 */
public class CollectionUtil {

  // Used list as an input because of getting element is needed.
  public static <T> Set<Set<T>> powerSet(List<T> list) {
    Set<Set<T>> powerSet = new HashSet<>();
    int setSize = list.size();
    long pow_set_size = (long) Math.pow(2, setSize);

    for (int counter = 0; counter < pow_set_size; counter++) {
      Set<T> subSet = new HashSet<>();
      for (int j = 0; j < setSize; j++) {
        if ((counter & (1 << j)) > 0) {
          subSet.add(list.get(j));
        }
      }
      powerSet.add(subSet);
    }
    return powerSet;
  }
}
