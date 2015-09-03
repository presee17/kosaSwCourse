package com.mycompany.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.dao.ProductDao;
import com.mycompany.myapp.dto.Product;

@Component
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public void add(Product product) {
		productDao.insert(product);
	}

	public List<Product> getPage(int pageNo, int rowsPerPage) {
		List<Product> list = productDao.selectByPage(pageNo, rowsPerPage);
		return list;
	}

	public Product getProduct(int ProductNo) {
		Product Product = productDao.selectByPk(ProductNo);
		return Product;
	}

	public void modify(Product Product) {
		productDao.update(Product);
	}

	public void remove(int ProductNo) {
		productDao.delete(ProductNo);

	}

	public void addHitcount(long ProductNo) {
		productDao.updateHitcount(ProductNo);
	}

	// ����¡ ���� �ʵ�� �޼ҵ�--------------------------------------------
	private int rowsPerPage = 10;
	private int pagesPerGroup = 3;

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public int getGroupNo(int pageNo) {
		return (pageNo - 1) / pagesPerGroup + 1;
		// int���� ����ϸ� ����
	}

	public int getStartPageNo(int groupNo) {
		return (groupNo - 1) * pagesPerGroup + 1;
	}

	public int getEndPageNo(int groupNo) {
		int startPageNo = getStartPageNo(groupNo);
		int totalGroupNo = getTotalGroupNo();
		if (totalGroupNo != groupNo) {
			return startPageNo + pagesPerGroup - 1;
		} else {
			return getTotalPageNo();
		}
	}

	public int getTotalPageNo() {
		int totalPageNo = 1;
		int rows = productDao.selectCount();
		totalPageNo = rows / rowsPerPage;
		if (rows % rowsPerPage != 0) {
			totalPageNo++;
		}
		return totalPageNo;
	}

	public int getTotalGroupNo() {
		int totalPageNo = getTotalPageNo();
		int totalGroupNo = getTotalPageNo() / pagesPerGroup;
		if (totalPageNo % pagesPerGroup != 0) {
			totalGroupNo++;
		}
		return totalGroupNo;
	}
}
