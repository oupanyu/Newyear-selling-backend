
class MarketAPI {

    static AddUserAPI(name, classnum, grade, sid, passwd) {
        return getAPIURL() + "/api/user/add?name=" + name + "&class=" + classnum + "&grade=" + grade + "&sid" + sid + "&passwd=" + passwd;
    }

    static getUserInfoAPI(sid){
        return getAPIURL()+"/api/user/getinfo?sid="+sid;
    }
    static getLoginAPI(sid,passwd){
        return getAPIURL()+"/api/user/login?sid="+sid+"&passwd="+passwd;
    }
    static getGetGoodsInfoAPI(gid){
        return getAPIURL()+"/api/goods/getinfo?gid="+gid
    }
    static getGoodsInfoByListAPI(pages){
        return getAPIURL()+"/api/goods/getinfo/bylist?page="+pages
    }
    static getAllGoodsInfoByListAPI(){
        return getAPIURL()+"/api/goods/getgoodinfo/all"
    }
    static getAddToCartAPI(token,sid,gid,count){
        return getAPIURL()+"/api/cart/add?token="+token+"&sid="+sid+"&gid="+gid+"&count="+count;
    }
    static getDelCartAPI(token,sid,gid){
        return getAPIURL()+"/api/cart/delete?token="+token+"&sid="+sid+"&gid="+gid;
    }
    static getGetCartInfoAPI(sid){
        return getAPIURL()+"/api/cart/get?sid="+sid;
    }

}

