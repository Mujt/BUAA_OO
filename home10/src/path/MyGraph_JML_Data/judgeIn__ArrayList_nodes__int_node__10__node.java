/*
 * Test data strategy for path.MyGraph.
 *
 * Generated by JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178), 2019-05-22 10:35 +0800.
 * (do not modify this comment, it is used by JMLUnitNG clean-up routines)
 */

 
package path.MyGraph_JML_Data;

import org.jmlspecs.jmlunitng.iterator.ObjectArrayIterator;
import org.jmlspecs.jmlunitng.iterator.RepeatedAccessIterator;

/**
 * Test data strategy for path.MyGraph. Provides
 * test values for parameter "int node" 
 * of method "boolean judgeIn(ArrayList, int)". 
 * 
 * @author JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178)
 * @version 2019-05-22 10:35 +0800
 */
public /*@ nullable_by_default */ class judgeIn__ArrayList_nodes__int_node__10__node
  extends ClassStrategy_int {
  /**
   * @return local-scope values for parameter 
   *  "int node".
   */
  public RepeatedAccessIterator<?> localValues() {
    return new ObjectArrayIterator<Object>
    (new Object[]
     { /* add local-scope int values or generators here */ });
  }
}
