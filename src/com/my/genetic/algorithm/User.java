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

	public void printPath() {
		System.out.println(Arrays.toString(this.current_path));
	}

	/**
	 * 重新规划路线
	 * 
	 * @param c_path 当前的规划路线
	 * @param v_num  走过了几个点
	 * @param start  选择出发点
	 */
	public void setPath(int[] c_path, int v_num, int start) {
		int index0 = v_num; // 用于出发点检测
		boolean start_exists = false;
		int index1 = 0; // 用于新任务点存储
		for (int i = v_num; i < c_path.length; i++) {  // 遍历当前路线，找出出发点所在位置
			if (c_path[i] == start) {
				start_exists = true;
				break;
			} else {
				index0++;
			}
		}
		if (!start_exists) {  // 如果不存在的话丢出错误
			throw new IllegalArgumentException("The start point has been visited!");
		}
		int temp = c_path[index0];  //将出发点位置放在新路线的第一个位置
		c_path[index0] = c_path[v_num];
		c_path[v_num] = temp;
		int[] user_pool = new int[c_path.length - v_num];
		for (int j = v_num; j < c_path.length; j++) {
			user_pool[index1] = c_path[j];
			index1++;
		}
		Population pop = new Population();
		Chromosome.setUserPool(user_pool);
		this.current_path = pop.start();
	}

	public static void main(String[] args) {
		User u = new User();
		u.setPath(u.getPath(), 2, 5);
	}
}
