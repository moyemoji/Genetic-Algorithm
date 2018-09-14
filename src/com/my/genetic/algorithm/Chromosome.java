package com.my.genetic.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Chromosome {
	private static int[] gene_pool = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
	private static int[] user_pool;
	public static double[][] distances = {
			{ 10000, 150, 350, 900, 421, 200, 1327, 476, 2261, 733, 831, 521, 862, 920, 274 },
			{ 150, 10000, 255, 871, 479, 294, 932, 1004, 873, 1207, 582, 313, 1179, 371, 582 },
			{ 350, 255, 10000, 739, 330, 124, 983, 1025, 2277, 639, 371, 82, 149, 880, 621 },
			{ 900, 871, 739, 10000, 538, 937, 682, 392, 85, 783, 1203, 336, 845, 323, 852 },
			{ 421, 479, 330, 538, 10000, 1732, 503, 842, 921, 283, 392, 724, 963, 210, 637 },
			{ 200, 294, 124, 937, 1732, 10000, 582, 391, 947, 836, 1394, 631, 773, 763, 134 },
			{ 1327, 932, 983, 682, 503, 582, 10000, 652, 863, 212, 572, 762, 273, 390, 682 },
			{ 476, 1004, 1025, 392, 842, 391, 652, 10000, 682, 721, 131, 683, 442, 489, 982 },
			{ 2261, 873, 2277, 85, 921, 947, 863, 682, 10000, 762, 314, 354, 521, 409, 131 },
			{ 733, 1207, 639, 783, 283, 836, 212, 721, 762, 10000, 309, 298, 351, 489, 314 },
			{ 831, 582, 371, 1203, 392, 1394, 572, 131, 314, 309, 10000, 133, 852, 314, 841 },
			{ 521, 313, 82, 336, 724, 631, 762, 683, 354, 298, 133, 10000, 672, 1532, 653 },
			{ 862, 1179, 149, 845, 963, 773, 273, 442, 521, 351, 852, 672, 10000, 682, 531 },
			{ 920, 371, 880, 323, 210, 763, 390, 489, 409, 489, 314, 1532, 682, 10000, 761 },
			{ 274, 582, 621, 852, 637, 134, 682, 982, 131, 314, 841, 653, 531, 761, 10000 } };

	/**
	 * 实数编码
	 */
	private int[] chroms;
	private double dist;
	private double adaptedness;

	/**
	 * 构造函数
	 */
	public Chromosome() {
		this.chroms = initChroms();
	}

	public static int[] getGenePool() {
		return gene_pool;
	}

	public static int[] getUserPool() {
		return user_pool;
	}

	public static void setUserPool(int[] pool) {
		user_pool = pool;
	}

	/**
	 * 获取染色体基因组
	 */
	public int[] getChroms() {
		return chroms;
	}

	/**
	 * 打印染色体基因组
	 */
	public void printChroms() {
		System.out.println(Arrays.toString(this.chroms));
	}

	public double getDistance() {
		return dist;
	}

	public void setDistance(double dist) {
		this.dist = dist;
	}

	/**
	 * 初始化染色体的基因组，fisher-yates shuffle i: n-1 => 1 j: random()*i => 1-i 交换a[i]和a[j]
	 * 范围、当前交换位置，剩余待交换位置
	 * 
	 * @return 返回初始化的基因组
	 */
	public int[] initChroms() {
		int len = user_pool.length;
		int[] gp = new int[len];
		for (int i = 0; i < len; i++) {
			gp[i] = user_pool[i];
		}

		for (int i = len - 1; i > 0; i--) {
			int j = (new Random()).nextInt(i + 1);
			while (j == 0) { // 除了起始点外打乱
				j = (new Random()).nextInt(i + 1);
			}
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
	public double getAdapt() {
		return adaptedness;
	}

	/**
	 * 设置适应度
	 * 
	 * @param adaptedness
	 */
	public void setAdapt(double adaptedness) {
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
		return copy;
	}

	public static Chromosome genetic(Chromosome p1, Chromosome p2) {
		if (p1 == null || p2 == null) { // 染色体一个为空时，不产生下一代
			return null;
		}
		if (p1.chroms == null || p2.chroms == null) { // 染色体有一个没有基因序列，不产生下一代
			return null;
		}

		Chromosome c1 = clone(p1);
		Chromosome c2 = clone(p2);
		Chromosome c3 = c1;

		int size = c1.chroms.length;
		int a = (int) (Math.random() * size);
		int b = (int) (Math.random() * size);
		int min = a > b ? b : a;
		int max = a > b ? a : b;

		for (int i = min; i < max; i++) { // 基因交叉
			c3.chroms[i] = 0;
		}

		int index = min;
		boolean flag = true;
		for (int j = 0; j < size; j++) {
			for (int v : c3.chroms) {
				if (v == c2.chroms[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				c3.chroms[index] = c2.chroms[j];
				index++;
			} else {
				flag = true;
			}
		}

		return c3;
	}

	/**
	 * 变异，必须是成对变异，即两个位置交换，所以变异数量应该少于总数减去2
	 * 
	 * @param num 变异的数量
	 */
	public void mutation(int num) {
		int size = user_pool.length;
		if (num > size - 2 || num < 0) {
			throw new IllegalArgumentException("Mutation failed. Required num<0 or num>size-2");
		} else {
			num = (num % 2 == 0) ? num : num - 1;
			for (int i = 0; i < num;) {
				int at1 = (int) (Math.random() * size);
				int at2 = (int) (Math.random() * size);
				while (at1 == 0) { // 除了起始点外可以变异
					at1 = (int) (Math.random() * size);
				}
				while (at2 == 0) {
					at2 = (int) (Math.random() * size);
				}
				int temp = chroms[at1];
				chroms[at1] = chroms[at2];
				chroms[at2] = temp;
				i += 2;
			}
		}
	}
}
