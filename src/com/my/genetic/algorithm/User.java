package com.my.genetic.algorithm;

import java.util.Arrays;

public class User {
	private int[] current_path; // GA返回的较优路径
	private int visited_num = 0; // 走过了几个点了

	/**
	 * 默认构造函数，刚构造出来的时候就给初始化一条最优化的路径
	 */
	public User() {
		Population pop = new Population();
		Chromosome.setUserPool(Chromosome.getGenePool());
		this.current_path = pop.start();
	}

	public int[] getPath() {
		return this.current_path;
	}

	/**
	 * 重新规划路线
	 * 
	 * @param c_path 当前的规划路线
	 * @param v_num 走过了几个点
	 */
	public void setPath(int[] c_path, int v_num) {
		int[] user_pool = new int[c_path.length - v_num];
		int index = 0;
		for (int i = v_num; i < c_path.length; i++) {
			user_pool[index] = c_path[i];
			index++;
		}
		System.out.println(Arrays.toString(user_pool));
		Population pop = new Population();
		Chromosome.setUserPool(user_pool);
		
		this.current_path = pop.start();
	}

	public static void main(String[] args) {
		User u = new User();
		u.setPath(u.getPath(), 2);
	}
}
