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
 * test values for parameter "Path path" 
 * of method "boolean containsPath(Path)". 
 * 
 * @author JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178)
 * @version 2019-05-22 10:35 +0800
 */
public /*@ nullable_by_default */ class containsPath__Path_path__27__path
  extends ClassStrategy_com_oocourse_specs2_models_Path {
  /**
   * @return local-scope values for parameter 
   *  "Path path".
   */
  public RepeatedAccessIterator<?> localValues() {
    return new ObjectArrayIterator<Object>
    (new Object[]
     { /* add local-scope com.oocourse.specs2.models.Path values or generators here */ });
  }

  /**
   * Constructor.
   * The use of reflection can be controlled here for  
   * "Path path" of method "boolean containsPath(Path)" 
   * by changing the parameters to <code>setReflective</code>
   * and <code>setMaxRecursionDepth<code>.
   * In addition, the data generators used can be changed by adding 
   * additional data class lines, or by removing some of the automatically 
   * generated ones. Since this is the lowest level of strategy, the 
   * behavior will be exactly as you specify here if you clear the existing 
   * list of classes first.
   *
   * @see NonPrimitiveStrategy#addDataClass(Class<?>)
   * @see NonPrimitiveStrategy#clearDataClasses()
   * @see ObjectStrategy#setReflective(boolean)
   * @see ObjectStrategy#setMaxRecursionDepth(int)
   */
  public containsPath__Path_path__27__path() {
    super();
    // uncomment to control the maximum reflective instantiation
    // recursion depth, 0 by default
    // setMaxRecursionDepth(0);
  }
}
