package ise.gameoflife.genetics;

/**
 * An evolvable being that can be evaluated for fitness.
 * Its behaviour is defined by a Genome instance of its own type.
 * @author Xitong Gao
 */
public interface Evolvable
{

	public Genome genome();
	public void setGenome(Genome aGenome);
	/**
	 * Evolvable being must update its data structures that define its
	 * behaviour when setting the genome.
	 * @param genome a genome matching SpecieGenome generic
	 */

	public double fitness();
	public void setFitness();

}
