package com.shenma.top.imagecopy.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shenma.top.imagecopy.dao.CollectProductDao;
import com.shenma.top.imagecopy.entity.CollectProduct;

@Service
public class GlobalService {
	@Autowired
	private CollectProductDao collectProductDao;
	
	public CollectProduct saveCollectProduct(CollectProduct entity){
		entity.setGmtCreate(new Date());
		return collectProductDao.saveAndFlush(entity);
	}
	


public Page<CollectProduct> listCollectProducts(int pageNo,int pageSize,Map<String,Object> params){
	Pageable pageable = new PageRequest(pageNo==0?pageNo:pageNo-1, pageSize, new Sort(Direction.DESC, new String[]{"id"}));
	Page<CollectProduct> retpage=collectProductDao.findAll(new Specification<CollectProduct>() {
		
		@Override
		public Predicate toPredicate(Root<CollectProduct> root, CriteriaQuery<?> query,
				CriteriaBuilder cb) {
			/*Path<String> pxcxq = root.get("yyDate");
			Path<String> activity = root.get("activityId");
			if(yyDate!=null){
				query.where(cb.like(pxcxq, yyDate+"%"),cb.equal(activity, activityId));
			}else{
				query.where(cb.equal(activity, activityId));
			}*/
			
			return null;
		}
	}, pageable);

	return retpage;
}

public void deleteById(Long id ){
	collectProductDao.delete(id);
}

@SuppressWarnings("unchecked")
public void batchDelete(List ids){
	List<CollectProduct> idsList=collectProductDao.findAll(ids);
	collectProductDao.deleteInBatch(idsList);
}
}