package com.my.genetic.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Chromosome {
	int[] gene_pool = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
	
	/**
	 * 实数编码
	 */
	private int[] chroms;
	private int adaptedness;

	/**
	 * 构造函数
	 */
	public Chromosome() {
		this.chroms = initChroms();
	}

	/**
	 * 获取染色体基因组
	 */
	public void getChroms() {
		System.out.println(Arrays.toString(this.chroms));
	}

	/**
	 * 初始化染色体的基因组，fisher-yates shuffle i: n-1 => 1 j: random()*i => 1-i 交换a[i]和a[j]
	 * 范围、当前交换位置，剩余待交换位置
	 * 
	 * @return 返回初始化的基因组
	 */
	public int[] initChroms() {
		int[] gp = gene_pool;
		int len = gene_pool.length;
		for (int i = len - 1; i > 0; i--) {
			int j = (new Random()).nextInt(i + 1);
			int temp = gp[i];
			gp[i] = gp[j];
			gp[j] = temp;
		}
		return gp;
	}

	/**
	 * 获取适应度
	 * 
	 * @return 返回adaptedness
	 */
	public int getAdapt() {
		return adaptedness;
	}

	/**
	 * 设置适应度
	 * 
	 * @param adaptedness
	 */
	public void setAdapt(int adaptedness) {
		this.adaptedness = adaptedness;
	}

	/**
	 * 染色体克隆
	 * 
	 * @param c
	 * @return 染色体的备份
	 */
	public static Chromosome clone(final Chromosome c) {
		if (c == null || c.chroms == null) {
			return null;
		}
		Chromosome copy = new Chromosome();
		for (int i = 0; i < c.chroms.length; i++) {
			copy.chroms[i] = c.chroms[i];
		}
		copy.getChroms();
		return copy;
	}

	/**
	 * 变异
	 * 
	 * @param num 变异的数量
	 */
	public void mutation(int num) {
		int size = gene_pool.length;
		if (num > size || num < 0) {
			throw new IllegalArgumentException("Mutation failed. Required num<0 or num>size");
		} else {
			num = (num % 2 == 0) ? num : num - 1;
			for (int i = 0; i < num;) {
				int at1 = (int) (Math.random() * size);
				int at2 = (int) (Math.random() * size);
				int temp = chroms[at1];
				chroms[at1] = chroms[at2];
				chroms[at2] = temp;
				i += 2;
			}
		}
	}

	public static void main(String[] args) {
		Chromosome c = new Chromosome();
		c.getChroms();
		c.mutation(4);
		c.getChroms();
	}

}
