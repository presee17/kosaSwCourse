package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.dto.Product;

@Component
public class ProductDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Integer insert(Product product){
		Integer pk = null;
		String sql = "insert into Team2_products (product_name, product_price, product_stock, product_detail) values (?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql, new String[] { "board_no" });
				pstmt.setString(1, product.getName());
				pstmt.setInt(2, product.getPrice());
				pstmt.setInt(3, product.getStock());
				pstmt.setString(4, product.getDetail());
				return pstmt;
			}
		}, keyHolder);
		Number keyNumber = keyHolder.getKey();
		pk = keyNumber.intValue();
		return pk;
	}

	public List<Product> selectByPage(int pageNo, int rowsPerPage){
		String sql = "";
		sql += "select product_no, product_name, product_price, product_stock, product_detail ";
		sql += "from Team2_products ";
		sql += "order by product_no desc ";
		sql += "limit ?,?";
		
		List<Product> list = jdbcTemplate.query(sql, new Object[] { (pageNo - 1) * rowsPerPage, rowsPerPage },
				new RowMapper<Product>() {
					@Override
					public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
						Product product = new Product();
						product.setNo(rs.getInt("product_no"));
						product.setName(rs.getString("product_name"));
						product.setPrice(rs.getInt("product_price"));
						product.setStock(rs.getInt("product_stock"));
						product.setDetail(rs.getString("product_detail"));
					return product;
					}
				});
		return list;
	}

		
	public Product selectByPk(int productNo){
		String sql = "select * from Team2_products where product_no = ?";
		
		Product product = jdbcTemplate.queryForObject(sql, new Object[] { productNo }, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setNo(rs.getInt("product_no"));
				product.setName(rs.getString("product_name"));
				product.setPrice(rs.getInt("product_price"));
				product.setStock(rs.getInt("product_stock"));
				product.setDetail(rs.getString("product_detail"));
				return product;
			}
		});
		return product;
	}
		
	
	public int update(Product product){
		String sql = "update Team2_products set product_name=?, product_price=?, product_stock=?, product_detail=? where product_no=?";
		int rows = jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getStock(), product.getDetail(), product.getNo());
		return rows;
	}

	public int delete(int productNo){
		String sql = "delete from Team2_products where product_no = ? ";
		int rows = jdbcTemplate.update(sql, productNo);
		return rows;
	}

	public int updateHitcount(long productNo){
	
		String sql = "update Team2_products set product_hitcount=product_hitcount+1 where product_no=?";
		int rows= jdbcTemplate.update(sql, productNo);
		return rows;
	}

	public int selectCount(){
		String sql = "select count(*) from Team2_products";
		int rows = jdbcTemplate.queryForObject(sql,Integer.class);
		return rows;
	}
}
