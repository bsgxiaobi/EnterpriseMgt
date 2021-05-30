import axios from "axios";
import qs from 'qs';

axios.defaults.baseURL = 'http://localhost:8082';//后端开发环境地址
// axios.defaults.headers.post['Content-Type'] = 'application/json';//配置请求头信息。
axios.defaults.headers.get['chareset'] = 'utf-8';
//封装一个无参数get请求实例--用于页面打开时获取数据
const instanceGetForNullParams = axios.create({
	//baseURL:'http://localhost:8080/axiosDemo',
	time:5000,
	method:'get',
	headers:{
		//'multipart/form-data' ，窗体数据被编码为一条消息，页上的每个控件对应消息中的一个部分
		//'application/x-www-form-urlencoded' 窗体数据被编码为名称/值对，这是标准的编码格式
		"Content-Type":'application/x-www-form-urlencoded',
	},
});
export function getDataForNullParams(url){
	// console.log('-----封装请求instanceGetForNullParams-----');
	return instanceGetForNullParams({url});
};

//封装一个get请求实例-带参-json格式
const instanceGetForParams = axios.create({
	//baseURL:'http://localhost:8080/axiosDemo',
	time:5000,
	method:'get',
	headers:{
		"Content-Type":'application/json',
	},
	// transformRequset:[
	// 	function(params){
	// 		return qs.stringify(params);
	// 	}
	// ],
});
export function GetForParams(url,params){
	// console.log('-----封装请求instanceGetForParams-----');
	// console.log(params);
	return instanceGetForParams({url,params});
}

//封装一个含数据的，以json格式，post提交的实例
const instancePostForComplex = axios.create({
	time:5000,
	method:'post',
	headers:{
		//'multipart/form-data' ，窗体数据被编码为一条消息，页上的每个控件对应消息中的一个部分
		//;boundary=ebf9f03029db4c2799ae16b5428b06bd
		//'application/x-www-form-urlencoded' 窗体数据被编码为名称/值对，这是标准的编码格式
		//application/json
		"Content-Type":'application/json',
	},
	// transformRequset:[
	// 	//提交前转化
	// 	function(data){
	// 		//序列化
	// 		return qs.stringify(data);
	// 	}
	// ],
});
export function submitDataForPost(url,data){
	// console.log('-----封装请求instancePostForComplex-----');
	// console.log(data);
	// console.log(qs.stringify(data));
	return instancePostForComplex({url,data});
}


//=================================================================//



//封装一个实例
const instancePost = axios.create({
	//baseURL:'http://localhost:8080/axiosDemo/api.php',
	//调用实例时，如果不传递完整链接，只传递“/api.php”,会自动添加到末尾
	baseURL:'http://localhost:8080/axiosDemo',
	time:5000,
	method:'post',
	headers:{
		//'multipart/form-data' ，窗体数据被编码为一条消息，页上的每个控件对应消息中的一个部分
		//'application/x-www-form-urlencoded' 窗体数据被编码为名称/值对，这是标准的编码格式
		"Content-Type":'application/x-www-form-urlencoded',
	},
	transformRequset:[
		//提交前转化
		function(data){
			//console.log('-----请求前转化的数据1-----');
			//转为url格式(有些接口无法识别提交的json数据)
			let str = '';
			
			for(let key in data){
				str += encodeURIComponent(key) + '=' + 
						encodeURIComponent(data[key]) + '&';
			}
			console.log('----请求前转化---');
			console.log(str);
			return str;
		}
	],
});

export function getData1(url,params){
	return axios.get(url,{
		params
	});
}

export function postData1(url,params){
	return axios.post(url,params,{
		headers:{
			//'multipart/form-data'
			"Content-Type":'application/x-www-form-urlencoded',
		},
		transformRequset:[
			//提交前转化
			function(data){
				//转为url格式(有些接口无法识别提交的json数据)
				let str = '';
				
				for(let key in data){
					str += encodeURIComponent(key) + '=' + 
							encodeURIComponent(data[key]) + '&';
				}
				console.log('-----请求前转化的数据postData1-----');
				console.log(str);
				return str;
			}
		],
	});
}

export function postData2(url,data){
	console.log('-----封装请求postData2-----');
	return instancePost({url,data});
}