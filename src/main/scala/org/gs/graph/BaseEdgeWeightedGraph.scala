/** @see http://algs4.cs.princeton.edu/43mst/EdgeWeightedGraph.java.html
 */
package org.gs.graph

import scala.collection.mutable.ListBuffer

/** Common code for [[org.gs.graph.EdgeWeightedGraph]], [[org.gs.diggraph.EdgeWeightedDigraph]]
 *  
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param <A> [[org.gs.graph.BaseEdge]] :> [[org.gs.graph.Edge]],[[org.gs.digraph.DirectedEdge]]
 * @param vertices in [[org.gs.graph.EdgeWeightedGraph]] or [[org.gs.digraph.EdgeWeightedDigraph]]
 */
abstract class BaseEdgeWeightedGraph[A <: BaseEdge](val V: Int) {
  require(V >= 0, s"Number of vertices, v:$V must be nonnegative")
  protected[gs] var e = 0
  protected[gs] val _adj = Array.fill[List[A]](V)(List[A]())

  protected def buildADJ[U <: BaseEdgeWeightedGraph[A]](g: U): Unit = {
    e = g.e

    for {
      v <- 0 until g.V
      reverse = List[A]()
    } {
      for {
        e <- g.adj(v)
      } e :: reverse
      for {
        er <- reverse
      } _adj(v) = er :: _adj(v)
    }
  }

  protected def rangeGuard(x: Int): Boolean = {
    x match {
      case x if 0 until V contains x => true
      case _ => false
    }
  }

  /** @return edges incident on @param v */
  def adj(v: Int): List[A] = {
    require(rangeGuard(v), s"verticies v:$v  not in 0..$V ")
    _adj(v)
  }

  /** @return edges in graph */
  def edges():List[A]

  override def toString(): String = {
    val lf = sys.props("line.separator")
    val sb = new StringBuilder()
    sb.append(s"$V $e $lf")
    def addLines(v: Int) {
      sb.append(s"$v : ")
      for {
        ed <- _adj(v)
      } sb.append(s"$ed  ")
      sb.append(lf)
    }
    for {
      v <- 0 until V
    } addLines(v)
    sb.toString
  }
}
