package com.shenma.top.imagecopy.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shenma.aliutil.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.aliutil.entity.trade.DeliveryAddress;
import com.shenma.aliutil.entity.wuliu.DeliveryTemplateDescn;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.BeanUtil;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.taobao.service.product.ItemService;
import com.shenma.taobao.util.TopAccessToken;
import com.shenma.top.imagecopy.dao.MqRecordDao;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoItemDao;
import com.shenma.top.imagecopy.ecxeption.DuplicateCopyException;
import com.shenma.top.imagecopy.entity.AliCopyConfig;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.entity.OwnCatInfo;
import com.shenma.top.imagecopy.entity.OwnCatInfoItem;
import com.shenma.top.imagecopy.service.ActiveMqService;
import com.shenma.top.imagecopy.service.AliBaBaSaveService;
import com.shenma.top.imagecopy.service.AliCopyConfigService;
import com.shenma.top.imagecopy.service.AlibabafinalSaveService;
import com.shenma.top.imagecopy.service.ProductCopyService;
import com.shenma.top.imagecopy.service.TaobaoFinalSaveService;
import com.shenma.top.imagecopy.service.TaobaoParseService;
import com.shenma.top.imagecopy.util.AliCateListAutoUtil;
import com.shenma.top.imagecopy.util.Constant;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.SaveProcessMsgManager;
import com.shenma.top.imagecopy.util.bean.SearchVoBean;
import com.shenma.top.imagecopy.util.exception.BusinessException;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemSkusGetResponse;

@Controller
@RequestMapping("/top/productcopy")
public class ProductCopyController {
    protected static Logger logger = Logger.getLogger("ProductCopyController");

    @Autowired
    private ProductCopyService copyService;

    @Autowired
    private ItemService itemService;


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CateService cateService;

    @Autowired
    private AlibabafinalSaveService aliBaBaSaveService;

    @Autowired
    private AliBaBaSaveService testService;

    @Autowired
    private ActiveMqService activeMqService;

    @Autowired
    private MqRecordItemDao mqRecordItemDao;

    @Autowired
    private MqRecordDao mqRecordDao;

    @Autowired
    private OwnCatInfoDao ownCatInfoDao;

    @Autowired
    private OwnCatInfoItemDao catInfoItemDao;

    @Autowired
    private TaobaoParseService taobaoParseService;

    @Autowired
    private TaobaoFinalSaveService taobaoFinalSaveService;

    @Autowired
    private SelfCatService selfCatService;

    @Autowired
    private WuliuService wuliuService; // 物流

    @Autowired
    private TradeService tradeService;

    @Autowired
    private AlbumService albumService;// 相册

    @Autowired
    private AliCopyConfigService aliCopyConfigService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/productIndex.jsp");
        return new ModelAndView("aceadmin/index", model);
    }

    @RequestMapping(value = "/searchShopUrl")
    @ResponseBody
    public SearchVoBean searchShopByUrl(@RequestBody Map<String, Object> variables) {
        SearchVoBean bean = null;
        try {
            bean = BeanUtil.map2Bean(variables, SearchVoBean.class);
            copyService.searchByBean(bean);

        } catch (ApiException e1) {
            logger.error("搜索店铺地址错误,地址为:" + bean.getUrl() + "," + bean.getCateUrl(), e1);
            bean.setErrorCode(e1.getErrCode());
            bean.setErrorMsg(e1.getErrMsg());
        } catch (Exception e) {
            logger.error("搜索店铺地址错误,地址为:" + bean.getUrl() + "," + bean.getCateUrl(), e);
            bean.setErrorCode("2");
            bean.setErrorMsg("手气不好,什么也没有搜到");
        }
        return bean;
    }

    @RequestMapping(value = "/searchOneItem")
    @ResponseBody
    public SearchVoBean searchOneItem(@RequestParam("url") String url) throws IOException, InterruptedException {
        SearchVoBean bean = copyService.searchOneItem(url);
        return bean;
    }


    @RequestMapping(value = "/items")
    @ResponseBody
    public SearchVoBean getitems(@RequestParam("url") String url, @RequestParam("pageNo") Integer pageNo, @RequestParam("shopType") Integer shopType, @RequestParam("cateUrl") String cateUrl) {
        SearchVoBean bean = new SearchVoBean();
        bean.setUrl(url);
        bean.setPageNo(pageNo);
        bean.setShopType(shopType);
        if (!StringUtils.isEmpty(cateUrl)) {
            bean.setCateUrl(cateUrl);
        }
        try {
            copyService.searchTaobaoItems(bean);
        } catch (Exception e) {
            logger.error(e);
            bean.setErrorCode("3");
            bean.setErrorMsg("手气不好,什么也没有搜到");
        }
        return bean;
    }

    @RequestMapping(value = "/item")
    @ResponseBody
    public SearchVoBean getitem(@RequestParam("url") String url) {
        SearchVoBean bean = new SearchVoBean();
        try {
            copyService.searchTaobaoItem(url);
        } catch (Exception e) {
            logger.error(e);

        }
        return bean;
    }

    @RequestMapping(value = "/taobaoitem")
    @ResponseBody
    public ItemGetResponse taobaoitem(@RequestParam("id") Long id) throws ApiException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("numIid", id);
        TopAccessToken accessToken = null;
        map.put("session", accessToken.getAccess_token());
        map.put("fields", "cid,seller_cids,props,pic_url,num,location,price,item_imgs,prop_imgs,detail_url,title,props_name,property_alias,input_pids,sku_properties,input_pids,input_str,skus");
        ItemGetResponse itemRes = itemService.findByOne(map);
        return itemRes;
    }

    @RequestMapping(value = "/sku")
    @ResponseBody
    public ItemSkusGetResponse getSkuByNumIid(@RequestParam("id") Long id) throws ApiException {
        TopAccessToken accessToken = null;
        ItemSkusGetResponse itemRes = itemService.findsKusById(id, accessToken.getAccess_token());
        return itemRes;
    }


    @RequestMapping(value = "/test")
    @ResponseBody
    public void test3(HttpServletRequest request) throws ApiException, AliReqException, IOException {
        String param = request.getParameter("");
        //aliBaBaSaveService.save("http://detail.1688.com/offer/42224365059.html");
        /*for(OfferDetailInfo info:vo.getToReturn()){
			params.put("offerId", info.getOfferId());
			params.put("returnFields", new String[]{"privateProperties","type","postCategryId","offerStatus","memberId","subject","productFeatureList","skuArray"});
			OfferDetailInfo temp=goodsService.getOffer(params);
			System.out.println(2);
		}*/
    }


    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public ModelAndView one(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/oneproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }

    @RequestMapping(value = "/recopy", method = RequestMethod.GET)
    public String recopy(@RequestParam("url") String url) throws ApiException {
        String preUrl = "";
        if (url.contains("taobao.com")) {
            preUrl += "/top/productcopy/taobaocopy";
        } else if (url.contains("1688.com")) {
            preUrl += "/top/productcopy/one";
        }
        return "redirect:" + preUrl + "?url=" + url;
    }


    /**
     * 获得整店复制的进度条
     *
     * @param request
     * @param response
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/saveProcessMsg", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> saveProcessMsg(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Integer> ret = new HashMap<String, Integer>(2);
        String memberId = SessionUtil.getAliSession().getMemberId();
        ret.put("total", SaveProcessMsgManager.getTotal(memberId));
        ret.put("currentNum", SaveProcessMsgManager.get(memberId));
        return ret;
    }

    @RequestMapping(value = "/saveByUrl")
    @ResponseBody
    public Map<String, Object> saveOneByUrl(@RequestBody Map<String, Object> variables) throws ApiException {
        String url = (String) variables.get("url");
        boolean picStatus = (boolean) variables.get("picStatus");
        MqRecordItem mqItem = new MqRecordItem();
        AliToken aliToken = SessionUtil.getAliSession();
        String userId = aliToken.getMemberId();
        mqItem.setUserId(userId);
        mqItem.setUrl(url);
        mqItem.setCreateTime(new Date());
        mqItem.setStatus(0);
        mqItem.setDelStatus(0);
        mqItem.setPicStatus(picStatus ? 1 : 0);
        mqItem.setUserName(aliToken.getSellerName());
        mqItem.setAcountName(aliToken.getResourceOwner());
        String oldOfferId = url.substring(url.indexOf("offer/") + 6, url.indexOf(".html"));
        mqItem.setOldOfferId(oldOfferId);
        //重复判断
        boolean ignoreType = (boolean) variables.get("ignoreType");

        if (ignoreType) {
            String ignoreTypeVal = (String) variables.get("ignoreTypeVal");
            if (ignoreTypeVal.equals("H")) {
                List<MqRecordItem> itmsList = mqRecordItemDao.findByUserIdAndOldOfferIdAndStatus(userId, oldOfferId, 1);
                System.out.println(itmsList.size());
                if (itmsList.size() > 0) {
                    Map<String, Object> ret = new HashMap<String, Object>();
                    ret.put("errorCode", "666");
                    ret.put("name", mqItem.getUrl());
                    ret.put("errorMsg", "已经自动忽略该地址的复制,因为已经存在该地址复制记录,如果要强行复制,请取消忽略重复商品选项");
                    return ret;
                }
            }
        }
        mqItem = mqRecordItemDao.saveAndFlush(mqItem);
        return saveItem(url, picStatus, mqItem, variables);
    }

    /**
     * 单个复制功能.
     *
     * @param url
     * @param picStatus
     * @param mqItem
     * @return
     */
    private Map<String, Object> saveItem(String url, boolean picStatus, MqRecordItem mqItem, Map<String, Object> selfInfo) {
        Offer offer = null;
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            offer = aliBaBaSaveService.save(url, picStatus, mqItem, selfInfo);
            //mqItem.setOldOfferId(oldOfferId);
            mqItem.setOfferId(offer.getOfferId().toString());
            mqItem.setTitle(offer.getSubject());
            //mqItem.setErrorMsg("复制成功");
            mqItem.setStatus(1);
            mqRecordItemDao.saveAndFlush(mqItem);
        } catch (AliReqException e) {
            mqItem.setStatus(2);
            mqItem.setErrorMsg(e.getMessage());
            mqRecordItemDao.saveAndFlush(mqItem);
            ret.put("errorCode", e.getCode());
            ret.put("errorMsg", e.getMessage());
            logger.error(e);
            return ret;
        } catch (DuplicateCopyException e) {
            mqRecordItemDao.delete(mqItem);
            ret.put("errorCode", e.getCode());
            ret.put("errorMsg", e.getMessage());
            return ret;
        } catch (BusinessException e) {
            mqItem.setStatus(2);
            mqItem.setErrorMsg(e.getMessage());
            mqRecordItemDao.saveAndFlush(mqItem);
            ret.put("errorCode", e.getCode());
            ret.put("errorMsg", e.getMessage());
            logger.error(e);
            return ret;
        } catch (Exception e) {
            mqItem.setStatus(2);
            String eMsg = e.toString();
            if (e.toString().length() > 250) {
                eMsg = e.toString().substring(0, 250);
            }
            mqItem.setErrorMsg("服务器错误!" + e);
            mqRecordItemDao.saveAndFlush(mqItem);
            ret.put("errorCode", 101);
            ret.put("errorMsg", "服务器错误!");
            logger.error(e);
            return ret;
        }
        ret.put("url", "http://detail.1688.com/offer/" + offer.getOfferId() + ".html");
        ret.put("editurl", "http://offer.1688.com/offer/post/fill_product_info.htm?offerId=" + offer.getOfferId() + "&operator=edit#");
        ret.put("name", offer.getSubject());
        return ret;
    }

    @RequestMapping(value = "/cate", method = RequestMethod.GET)
    public ModelAndView cate(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/cateproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }

    @RequestMapping(value = "/saveByCate")
    @ResponseBody
    public List<Map<String, Object>> saveOneByCate(@RequestBody Map<String, Object> variables) throws ApiException {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(2);
        List<String> urlList = (List<String>) variables.get("urlList");
        for (String url : urlList) {
            variables.put("url", url);
            try {
                Map<String, Object> retone = saveOneByUrl(variables);
                ret.add(retone);
                SaveProcessMsgManager.increase(SessionUtil.getAliSession().getMemberId());
            } catch (Exception e) {
                logger.error(e);
            }
        }
        SaveProcessMsgManager.remove(SessionUtil.getAliSession().getMemberId());
        //activeMqService.sendMessage(urlList);
        return ret;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView all(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/allproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }

    @RequestMapping(value = "/saveAll")
    @ResponseBody
    public String saveAll(@RequestBody Map<String, Object> variables) throws ApiException, InterruptedException {
        String url = (String) variables.get("url");
        if (url.indexOf("1688.com") > -1) {
            url = url.substring(0, url.indexOf("1688.com") + 8);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cate", "");
        params.put("url", url);
        SearchVoBean searchVoBean = this.searchShopByUrl(params);
        List<String> urlList = new ArrayList<String>();
        if (searchVoBean.getTotalPages() != null) {
            for (int i = 1; i < searchVoBean.getTotalPages() + 1; i++) {
                SearchVoBean bean = this.getitems(url, i, 1, "");
                Thread.sleep(Constant.search_cate_delay_time);//搜索的限制功能
                for (Item item : bean.getItems()) {
                    urlList.add(item.getDetailUrl());
                }
            }
        }
        SaveProcessMsgManager.setTotal(SessionUtil.getAliSession().getMemberId(), urlList.size());
        variables.put("urlList", urlList);
        variables.put("picStatus", false);
        saveOneByCate(variables);

        //activeMqService.sendMessage(urlList);
        return "success";
    }


    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView history(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/history.jsp");
        return new ModelAndView("aceadmin/index", model);
    }

    @RequestMapping(value = "/mqRecordList")
    @ResponseBody
    public Page<MqRecordItem> mqRecordList(@RequestParam("pageNo") Integer pageNo, @RequestParam("status") Integer status) throws ApiException {
        Page<MqRecordItem> ret = testService.MqRecordItemList(pageNo, 20, SessionUtil.getAliSession().getMemberId(), status);
		/*try {
			aliBaBaSaveService.genCatesToDb();
		} catch (AliReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Page<MqRecordItem> ret=null;*/
        return ret;
    }

    @RequestMapping(value = "/delhistory")
    @ResponseBody
    public String delhistory(@RequestBody Map<String, Object> variables) throws ApiException {
        List<String> ids = (List<String>) variables.get("ids");
        List<Integer> idsIn = new ArrayList<Integer>();
        for (String i : ids) {
            idsIn.add(Integer.valueOf(i));
        }
        testService.delHistory(idsIn);
        return "success";
    }


    /**
     * 淘宝复制到阿里index页面请求
     *
     * @param request
     * @param response
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/taobaocopy", method = RequestMethod.GET)
    public ModelAndView copytaobaoToaliIndex(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/taobao/oneproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }

    /**
     * 批量淘宝复制到阿里index页面请求
     *
     * @param request
     * @param response
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/batchtaobaocopy", method = RequestMethod.GET)
    public ModelAndView batchtaobaocopy(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/taobao/batchproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }

    /**
     * 批量阿里复制到阿里index页面请求
     *
     * @param request
     * @param response
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/batchalicopy", method = RequestMethod.GET)
    public ModelAndView batchaliocopy(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/batchproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }


    /**
     * 类目淘宝复制到阿里index页面请求
     *
     * @param request
     * @param response
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/catetaobaocopy", method = RequestMethod.GET)
    public ModelAndView copytaobaoToaliCateIndex(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/taobao/cateproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }


    /**
     * 根据查询条件,请求类目列表
     *
     * @param url
     * @return
     * @throws ApiException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/alicateList", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> alicateList(@RequestParam("url") String url) throws ApiException, JsonParseException, JsonMappingException, IOException {
        String liststr = AliCateListAutoUtil.get(url, new HashMap(), "gbk");
        Map<String, Object> rootdata = JacksonJsonMapper.getInstance().readValue(AliCateListAutoUtil.getData(liststr), HashMap.class);
        List<Map<String, Object>> catelist = (List<Map<String, Object>>) rootdata.get("data");
        return catelist;
    }

    /**
     * 根据catid,请求产品属性和产品规格
     *
     * @param catsId
     * @return
     * @throws ApiException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/propertiesByCatid", method = RequestMethod.GET)
    @ResponseBody
    public String propertiesByCatid(@RequestParam("catsId") String catsId) {
        OwnCatInfo catInfo = ownCatInfoDao.findByCatsId(Integer.valueOf(catsId));
        return catInfo == null ? "" : catInfo.getProperties();
    }

    @RequestMapping(value = "/parseTaobaoUrlByUrl", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> parseTaobaoUrlByUrl(@RequestParam("url") String url) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret = taobaoParseService.parseDataByUrl(url);
        return ret;
    }

    /**
     * 根据catid,请求产品属性和产品规格
     *
     * @param catsId
     * @return
     * @throws ApiException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/propertiesByPath", method = RequestMethod.GET)
    @ResponseBody
    public String propertiesByPath(@RequestParam("catsId") String catsId, @RequestParam("path") String path) {
        OwnCatInfoItem catInfo = catInfoItemDao.findByCatIdAndPathValues(Integer.valueOf(catsId), path);
        return catInfo == null ? "" : catInfo.getProperties();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/saveToTabao", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveToTabao(@RequestBody Map<String, Object> variables) {
        String url = variables.get("url").toString();
        String catId = variables.get("catId").toString();
        Boolean picStatus = Boolean.valueOf(variables.get("picStatus").toString());
        MqRecordItem mqItem = new MqRecordItem();
        AliToken aliToken = SessionUtil.getAliSession();
        mqItem.setUserId(aliToken.getMemberId());
        mqItem.setUrl(url);
        mqItem.setCreateTime(new Date());
        mqItem.setStatus(0);
        mqItem.setDelStatus(0);
        mqItem.setPicStatus(picStatus ? 1 : 0);
        mqItem.setUserName(aliToken.getSellerName());
        mqItem.setAcountName(aliToken.getResourceOwner());
        String querystr = url.substring(url.indexOf("id=") + 3, url.length());
        String oldOfferId = querystr.split("&")[0];
        mqItem.setOldOfferId(oldOfferId);
        //重复判断
        boolean ignoreType = (boolean) variables.get("ignoreType");
        if (ignoreType) {
            String ignoreTypeVal = (String) variables.get("ignoreTypeVal");
            if (ignoreTypeVal.equals("H")) {
                List<MqRecordItem> itmsList = mqRecordItemDao.findByUserIdAndOldOfferIdAndStatus(aliToken.getMemberId(), oldOfferId, 1);
                if (itmsList.size() > 0) {
                    Map<String, Object> ret = new HashMap<String, Object>();
                    ret.put("errorCode", "666");
                    ret.put("name", mqItem.getUrl());
                    ret.put("errorMsg", "已经自动忽略该地址的复制,因为已经存在该地址复制记录,如果要强行复制,请取消忽略重复商品选项");
                    return ret;
                }
            }
        }
        mqItem = mqRecordItemDao.saveAndFlush(mqItem);
        Offer offer = null;
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            offer = taobaoFinalSaveService.save(url, catId, picStatus, mqItem, variables);

            mqItem.setOldOfferId(oldOfferId);
            mqItem.setOfferId(offer.getOfferId().toString());
            //mqItem.setErrorMsg("复制成功");
            mqItem.setStatus(1);
            mqRecordItemDao.saveAndFlush(mqItem);
        } catch (AliReqException e) {
            mqItem.setStatus(2);
            mqItem.setErrorMsg(catId.toString() + e.getMessage() + ":" + e.getCode());
            mqRecordItemDao.saveAndFlush(mqItem);
            ret.put("errorCode", e.getCode());
            ret.put("errorMsg", e.getMessage());
            ret.put("name", mqItem.getTitle());
            logger.error(e);
            return ret;
        } catch (DuplicateCopyException e) {
            mqRecordItemDao.delete(mqItem);
            ret.put("errorCode", e.getCode());
            ret.put("errorMsg", e.getMessage());
            logger.error(e);
            return ret;
        } catch (BusinessException e) {
            mqItem.setStatus(2);
            mqItem.setErrorMsg(catId.toString() + e.getMessage());
            mqRecordItemDao.saveAndFlush(mqItem);
            ret.put("errorCode", e.getCode());
            ret.put("errorMsg", e.getMessage());
            ret.put("name", mqItem.getTitle());
            logger.error(e);
            return ret;
        } catch (Exception e) {
            mqItem.setStatus(2);
            String eMsg = e.toString();
            if (e.toString().length() > 250) {
                eMsg = e.toString().substring(0, 250);
            }
            mqItem.setErrorMsg(catId.toString() + "服务器错误!" + eMsg);
            mqRecordItemDao.saveAndFlush(mqItem);
            ret.put("errorCode", 101);
            ret.put("errorMsg", "服务器错误!");
            ret.put("name", mqItem.getTitle());
            logger.error(e);
            return ret;
        }
        ret.put("url", "http://detail.1688.com/offer/" + offer.getOfferId().toString() + ".html");
        ret.put("editurl", "http://offer.1688.com/offer/post/fill_product_info.htm?offerId=" + offer.getOfferId().toString() + "&operator=edit");
        ret.put("name", offer.getSubject());
        return ret;
    }


    /**
     * 淘宝批量复制,淘宝类目复制
     *
     * @param variables
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/batchTaobaoAll")
    @ResponseBody
    public List<Map<String, Object>> batchTaobaoAll(@RequestBody Map<String, Object> variables) throws ApiException {
        List<String> urlList = (List<String>) variables.get("urlList");
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        for (String url : urlList) {
            variables.put("url", url);
            Map<String, Object> ret = saveToTabao(variables);
            retList.add(ret);
        }
        return retList;
    }


    /**
     * 重新复制
     *
     * @param url
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/resave")
    @ResponseBody
    public Map<String, Object> resave(@RequestParam("id") String id) throws ApiException {

        MqRecordItem mqItem = mqRecordItemDao.findOne(Integer.valueOf(id));
        mqItem.setStatus(0);
        mqRecordItemDao.saveAndFlush(mqItem);
        return saveItem(mqItem.getUrl(), mqItem.getPicStatus() == 0 ? false : true, mqItem, new HashMap<String, Object>());
    }

    @RequestMapping(value = "/findSelfCate")
    @ResponseBody
    public Map<String, Object> findSelfCate() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ;
        try {
            Map<String, Object> obj = selfCatService.getAllCate();
            ret.put("cates", obj);
        } catch (AliReqException e) {
            if (e.getCode().equals("400")) {
                //ret.put("cates", "加载自定义分类出错");
            } else {
                ret.put("errorMsg", "加载自定义分类出错");
            }
        }
        return ret;
    }

    @RequestMapping(value = "/getTemplates")
    @ResponseBody
    public List<DeliveryTemplateDescn> getTemplates() throws AliReqException {
        // 物流运费信息
        List<DeliveryTemplateDescn> wuliList = wuliuService.getAllDeliveryTemplateDescn();
        if (wuliList == null) wuliList = new ArrayList<DeliveryTemplateDescn>();
        return wuliList;
    }

    /**
     * 获得地址列表
     *
     * @return
     * @throws AliReqException
     */
    @RequestMapping(value = "/getSendGoodsAddresses")
    @ResponseBody
    public List<DeliveryAddress> getSendGoodsAddresses() throws AliReqException {
        // 物流运费信息
        List<DeliveryAddress> wuliList = tradeService.freightSendGoodsAddressList();
        if (wuliList == null) wuliList = new ArrayList<DeliveryAddress>();
        return wuliList;
    }

    /**
     * 反馈
     *
     * @param id
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/fankui")
    @ResponseBody
    public String fankui(@RequestParam("id") String id) throws ApiException {
        MqRecordItem mqItem = mqRecordItemDao.findOne(Integer.valueOf(id));
        mqItem.setStatus(3);
        mqRecordItemDao.saveAndFlush(mqItem);
        return "success";
    }

    @RequestMapping(value = "/owntest")
    @ResponseBody
    public String test() throws AliReqException, JsonParseException, JsonMappingException, IOException, InterruptedException {
        //testService.genCatesToDb();
        return "success";
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/upfiletest")
    @ResponseBody
    public String upifletest() throws ApiException, IOException, AliReqException {
        List<Album> list = albumService.getAllAlbumList();
        byte[] imageBytes = FileUtils.readFileToByteArray(new File("d:\\test1.gif"));
        Map<String, Object> ret = albumService.uploadImage(list.get(0).getId(), "test123", "测试", imageBytes);
        return "success";
    }

    /**
     * 保存自定义设置
     *
     * @param variables
     * @return
     * @throws ApiException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/saveAliConfig")
    @ResponseBody
    public AliCopyConfig saveAliConfig(@RequestBody Map<String, Object> variables) throws ApiException, JsonGenerationException, JsonMappingException, IOException {
        String aliconfig = JacksonJsonMapper.getInstance().writeValueAsString(variables);
        AliCopyConfig entity = aliCopyConfigService.saveOrUpdate(aliconfig);
        return entity;
    }

    /**
     * 保存自定义设置
     *
     * @return
     * @throws ApiException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/loadAliConfig")
    @ResponseBody
    public AliCopyConfig loadAliConfig() throws ApiException, JsonGenerationException, JsonMappingException, IOException {
        AliCopyConfig entity = aliCopyConfigService.findOne();
        return entity;
    }


    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ModelAndView customerIndex(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "copy/customer/cateproduct.jsp");
        return new ModelAndView("aceadmin/index", model);
    }
}
