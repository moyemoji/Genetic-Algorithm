package com.my.genetic.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Population {
	private List<Chromosome> population = new ArrayList<Chromosome>();

	private int pop_size = 40; // 种群数量
	private int max_iter_num = 1000; // 最大迭代次数
	private double compete_rate = 0.15; // 参与竞赛的个体比例
	private double mutation_rate = 0.001; // 基因变异概率
	private int max_mutation_num = 5; // 最大变异步长
	private int generation = 1; // 当前遗传到第几代
	private double pre_distance = 0; // 上一代最短距离
	private int dis_count = 0;
	private int min_converge_num = 20; // 收敛条件

	private double total_dist;
	private double best_adaptedness;
	private double total_adaptedness;
	private double average_adaptedness;

	private Chromosome best_chroms;
	private int best_generation;

	/**
	 * 初始化种群，生成制定个数的个体
	 */
	private void initPopulation() {
		System.out.println("2.Initing population...");
		for (int i = 0; i < pop_size; i++) {
			Chromosome chroms = new Chromosome();
			population.add(chroms);
		}
	}

	private void setAdaptedness() {
		for (Chromosome chroms : population) {
			double adaptedness = 0;
			int[] gc = chroms.getChroms();
			for (int i = 0; i < gc.length - 1; i++) {
				adaptedness += Chromosome.distances[gc[i] - 1][gc[i + 1] - 1];
			}
			adaptedness += Chromosome.distances[gc[gc.length - 1] - 1][gc[0]-1];
			chroms.setDistance(adaptedness);
			chroms.setAdapt(1 / adaptedness * 10000);
		}
	}

	/**
	 * 计算种群适应度，找出当代最好的个体以及总适应度，平均适应度
	 */
	private void calculateAdaptedness() {
		System.out.println("3.Calculating population adaptedness...");
		setAdaptedness();
		best_adaptedness = population.get(0).getAdapt();
		total_adaptedness = 0;
		for (Chromosome chroms : population) {
			if (chroms.getAdapt() > best_adaptedness) {
				best_adaptedness = chroms.getAdapt();
				best_chroms = chroms;
				best_generation = generation;
			}
			total_adaptedness += chroms.getAdapt();
		}
		average_adaptedness = total_adaptedness / pop_size;
	}

//	/**
//	 * 轮盘赌法确定亲代，太慢了
//	 * 
//	 * @return
//	 */
//	private Chromosome getParentChroms() {
//		while (true) {
//			double slice = Math.random() * total_adaptedness;
//			double sum = 0;
//			for (Chromosome chroms : population) {
//				sum += chroms.getAdapt();
//				if (sum > slice && chroms.getAdapt() >= average_adaptedness) {
//					System.out.println("return");
//					return chroms;
//				}
//			}
//		}
//	}

	/**
	 * 锦标赛选择法
	 * 
	 * @return
	 */
	private Chromosome getParentChroms() {
		int num = (int) (pop_size * compete_rate);
		List<Chromosome> list = new ArrayList<Chromosome>();

		for (int i = 0; i < num; i++) {
			int index = (int) (Math.random() * population.size());
			list.add(population.get(index));
		}

		int best_index = 0;
		double adaptedness = list.get(0).getAdapt();
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).getAdapt() > adaptedness) {
				best_index = j;
			}
		}

		return list.get(best_index);
	}

	/**
	 * 种群进化，随机选取亲代，繁殖出同等数量的子代
	 */
	private void populationEvolve() {
		System.out.println("4.Population is evolving...");
		List<Chromosome> child_population = new ArrayList<Chromosome>();
		while (child_population.size() < pop_size) {

			Chromosome p1 = getParentChroms();
			Chromosome p2 = getParentChroms();
			Chromosome child = Chromosome.genetic(p1, p2);
			child_population.add(child);
		}

		population.clear();
		population = child_population;
	}

	/**
	 * 变异，种群依照一定的概率变异，变异的基因数量一定
	 */
	private void populationMutation() {
		System.out.println("5.Population is mutating...");
		for (Chromosome chroms : population) {
			if (Math.random() < mutation_rate) {
				int num = (int) (Math.random() * max_mutation_num);
				chroms.mutation(num);
			}
		}

	}

	private void printAdaptedness() {
		System.out.print("========================================");
		System.out.println("======================================");
		System.out.println("Generation: " + generation);
		System.out.println("Best Adaptedness is: " + best_adaptedness);
		System.out.println("Shortest Distance is: " + best_chroms.getDistance());
		System.out.println("Its chromosome is: " + Arrays.toString(best_chroms.getChroms()));
		System.out.print("========================================");
		System.out.println("======================================\n");
	}

	private boolean convergeCheck() {
		if (best_chroms.getDistance() == pre_distance) {
			dis_count++;
		} else {
			pre_distance = best_chroms.getDistance();
			dis_count = 1;
		}
		if (dis_count > min_converge_num) {
			return true;
		} else {
			return false;
		}
	}

	public int[] start() {
		System.out.println("1.Start Calculating...");
		initPopulation();
		for (generation = 1;; generation++) {
			calculateAdaptedness();
			if (convergeCheck()) {
				break;
			}
			populationEvolve();
			populationMutation();
			printAdaptedness();
		}
		
		return best_chroms.getChroms();
	}
}
