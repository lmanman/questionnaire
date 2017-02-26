package com.visionet.letsdesk.app.exhibition.repository;

import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurveyPublicShow;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ExhibitionSurveyDaoImpl {

    @PersistenceContext
    private EntityManager em;

    public Page<ExhibitionSurvey> searchByCondition(final ExhibitionSurveyVo exhibitionSurveyVo,final List<String> checkboxNameList,final PageInfo pageinfo) throws Exception{

        String query = " from ExhibitionSurvey t where 1=1 ";

        Map<String,Object> map = Maps.newHashMap();

        if(Validator.isNotNull(exhibitionSurveyVo.getQueryBeginDate())){
            Date beginDate;
            if(exhibitionSurveyVo.getQueryEndDate().trim().length()==19 && exhibitionSurveyVo.getQueryEndDate().contains(StringPool.COLON)){
                beginDate= DateUtil.convertFromString(exhibitionSurveyVo.getQueryEndDate());
            }else{
                beginDate= DateUtil.convertDate(exhibitionSurveyVo.getQueryEndDate());
            }
            query += " and t.createDate >= :queryBeginDate";
            map.put("queryBeginDate", beginDate);
        }
        if(Validator.isNotNull(exhibitionSurveyVo.getQueryEndDate())){
            Date endDate;
            if(exhibitionSurveyVo.getQueryEndDate().trim().length()==19 && exhibitionSurveyVo.getQueryEndDate().contains(StringPool.COLON)){
                endDate= DateUtil.convertFromString(exhibitionSurveyVo.getQueryEndDate());
            }else{
                endDate= DateUtil.seekDate(DateUtil.convertDate(exhibitionSurveyVo.getQueryEndDate()), 1);
            }
            query += " and t.createDate <= :queryEndDate";
            map.put("queryEndDate", endDate);
        }

        List<ExhibitionSurveyPublicShow> publicShowList = exhibitionSurveyVo.getPublicShowList();
        if(Collections3.isNotEmpty(publicShowList)){
            //只接收第一行元素查询
            ExhibitionSurveyPublicShow publicShow = publicShowList.get(0);
            query += " and  EXISTS (select 1 from ExhibitionSurveyPublicShow s where s.surveyId=t.id ";
            if(publicShow.getPublicExhibitionArea()!=null){
                query += " and s.publicExhibitionPriceTag="+publicShow.getPublicExhibitionPriceTag();
            }
            if(publicShow.getPublicExhibitionArea()!=null){
                query += " and s.publicExhibitionArea="+publicShow.getPublicExhibitionArea();
            }
            if(publicShow.getPublicExhibitionArea()!=null){
                query += " and s.publicExhibitionFloor="+publicShow.getPublicExhibitionFloor();
            }
            if(publicShow.getPublicExhibitionArea()!=null){
                query += " and s.publicExhibitionPlace="+publicShow.getPublicExhibitionPlace();
            }
            query += ") ";
        }

        BeanInfo beanInfo = Introspector.getBeanInfo(exhibitionSurveyVo.getClass());
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            Method readMethod = descriptor.getReadMethod();
            if(readMethod!=null) {
                Object result = readMethod.invoke(exhibitionSurveyVo, new Object[0]);
                if (result != null) {
                    if (result instanceof Integer || result instanceof Long) {
                        query += " and t."+propertyName+" = :"+propertyName;
                        map.put(propertyName, result);
                    }else if(result instanceof List){
                        if(checkboxNameList.contains(propertyName) && !((List) result).isEmpty()){
                            query += " and  EXISTS (select 1 from ExhibitionSurveyMultiselect m where m.surveyId=t.id and m.surveyField='"+propertyName+"' and m.sundryId in(:"+propertyName+"))";
                            map.put(propertyName, (List<Integer>)result);
                        }
                    }
                }
            }
        }

        String countSql = "select count(distinct t.id) " + query;
        String listSql = "select t "+ query + " order by t.id asc ";

        Long count = 0L;
        List<ExhibitionSurvey> list = null;
        EntityManager entityManager = null;
        try {
            entityManager = em.getEntityManagerFactory().createEntityManager();
            Query queryString = entityManager.createQuery(listSql);
            Query queryCount = entityManager.createQuery(countSql);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                queryString.setParameter(entry.getKey(), entry.getValue());
                queryCount.setParameter(entry.getKey(), entry.getValue());
            }

            queryString.setFirstResult(pageinfo.getCurrentPageNumber());
            queryString.setMaxResults(pageinfo.getPageSize());

            count = (Long) queryCount.getSingleResult();
            list = queryString.getResultList();

        }catch (RuntimeException e){
            throw e instanceof IllegalArgumentException ? e : new IllegalArgumentException(e);
        }finally {
            if(entityManager!=null) {
                EntityManagerFactoryUtils.closeEntityManager(entityManager);
            }
        }
        return new PageImpl<ExhibitionSurvey>(list, new PageRequest(pageinfo.getPageNumber(),
                pageinfo.getPageSize(), new Sort(pageinfo.getSortType(), pageinfo.getSortColumn())), count);
    }

}
