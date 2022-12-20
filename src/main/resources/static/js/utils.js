String.prototype.format = function(){
    if(arguments.length==0){
      return this;
    }
    for(var s=this, i=0; i<arguments.length; i++){
      s = s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);
    }
    return s;
  };

function getOnSaleDiv(href_url,name,description,left,pic,gid) {
    if (pic === 0){
        p = "images/replace.png"
    }else {
        p = "images/"+gid+".jpg"
    }
    div_child1 = '<div class="card card-body"><a href="'+ href_url +'" class="card_link"><div class="row"><div class="col-4"><img src="' + p+ '" class="card-img-top"></div><div class="col-8"><h4 class="card-title" id="'
    div_child2 = '">【社团名称】产品名称</h4><p class="card-text" id="'
    div_child3 = '">产品简介</p><p class="btn btn-primary" id="'
    div_child4 = '">剩余库存: 123</p></div></div></a></div>'
    return div_child1 + name + div_child2 + description + div_child3 + left + div_child4
  }

  function getForSaleDiv(href_url,name,description,left,pic,gid){
      if (pic === 0){
          p = "images/replace.png"
      }else {
          p = "images/"+gid+".jpg"
      }
    var dv = '<div class="card card-body"><a href="' + href_url + '" class="card_link"><div class="row"><div class="col-4"><img src="'+p+'" class="card-img-top"></div><div class="col-8"><h4 class="card-title" id = "' +name+ '">【社团名称】产品名称</h4><p class="card-text" id="' + description+ '">产品简介：</p><p class="btn btn-primary" id = "'+ left +'">剩余库存: 234</p></div></div></a></div></div>'
    return dv
  }

const USE_HTTPS = 0
const API_METHOD = 0  //0:use localhost ===  1:use remote host
const API_URL = "localhost"
const API_PORT = 8080

function getAPIURL(){
  if(API_METHOD === 0){
    if(USE_HTTPS === 0){
      return "http://localhost:"+API_PORT
    }else{
      return "https://localhost:"+API_PORT
    }

  }else{
    if(USE_HTTPS === 0){
      return "http://" + API_URL + ":" + API_PORT
    }else{
      return "https://" + API_URL + ":" +  API_PORT
    }
  }
}