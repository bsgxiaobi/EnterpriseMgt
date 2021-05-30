<template>
	<div id="visit-form-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="回访客户:">
				<!-- <el-input v-model="suggestedInput" placeholder="回访客户"></el-input> -->
				<el-autocomplete
				      class="inline-input"
				      v-model="suggestedInput"
				      :fetch-suggestions="querySearch"
				      placeholder="请输入内容"
				      @select="handleSelect">
					  <template #default="{ item }">
						  <span>{{item.name}}</span>
						  </template>
				</el-autocomplete>
			</el-form-item>
			<el-form-item label="联系人:">
				<el-autocomplete
				      class="inline-input"
				      v-model="suggestedInputSon"
				      :fetch-suggestions="querySearchSon"
				      placeholder="请输入内容"
				      @select="handleSelectSon">
					  <template #default="{ item }">
						  <span>{{item.name}}</span>
						  </template>
				</el-autocomplete>
			</el-form-item>
			<el-form-item label="回访方式:">
			  <el-select v-model="formData.type" placeholder="请选择">
			    <el-option label="电话" value="电话"></el-option>
			    <el-option label="邮件" value="邮件"></el-option>
				<el-option label="登门拜访" value="登门拜访"></el-option>
				<el-option label="生日祝福" value="生日祝福"></el-option>
				<el-option label="邀约" value="邀约"></el-option>
				<el-option label="网络洽谈" value="网络洽谈"></el-option>
				<el-option label="其他" value="其他"></el-option>
			  </el-select>
			</el-form-item>
			
			<div class="el-item-textarea">
				<label id="el-item-textarea-label-frist">交谈内容:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.content" placeholder="备注"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">心得总结:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.summary" placeholder="备注"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {
	cvId:'',cid:'',ccId:'',type: '',summary:'',
	content: '',visitDate: '',agent: '',agentDate: '',
	customerInfo:{name:''},customerContacts:{name:''}
};
let suggestedInput = '';
let suggestedInputSon = '';
let suggestedList = [];
let suggestedListSon = [];
import {GetForParams,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'VisitForm',
	props:{
		propVisit:Object
	},
	data(){
		return{
			suggestedInput,suggestedInputSon,suggestedList,suggestedListSon,
			formData
		}
	},
	methods:{
		querySearch(queryString, cb){
		    let results = this.suggestedList.filter(item => {
				if (item.name.indexOf(queryString) > -1) {
					return true;
				}
		    });
		    // 调用 callback 返回建议列表的数据
		    cb(results);
		},
		handleSelect(item){
			this.suggestedInput = item.name;
			this.formData.cid = item.cid;
			this.suggestedInputSon = '';
			this.formData.customerInfo.name = this.suggestedInput;
			this.getLoadAllSon(item.cid);
		},
		querySearchSon(queryString, cb){
		    let results = this.suggestedListSon.filter(item => {
				if (item.name.indexOf(queryString) > -1) {
					return true;
				}
		    });
		    // 调用 callback 返回建议列表的数据
		    cb(results);
		},
		handleSelectSon(item){
			this.suggestedInputSon = item.name;
			this.formData.ccId = item.ccId;
			this.formData.customerContacts.name = this.suggestedInputSon;
		},
		resetData(){
			this.formData = {
				cvId:'',cid:'',ccId:'',type: '',summary:'',
				content: '',visitDate: '',agent: '',agentDate: '',
				customerInfo:{name:''},customerContacts:{name:''}
			};
			this.suggestedInput = '';
			this.suggestedInputSon = '';
		},
		async getLoadAll(){
			getDataForNullParams("/customer-info/all").then(res=>{
				this.suggestedList = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取客户的所有联系人信息
		async getLoadAllSon(cid){
			let params = {};
			params.cid = cid;
			GetForParams("/customer-contacts/all",params).then(res=>{
				this.suggestedListSon = res.data.data;
				// console.log(this.suggestedListSon);
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		if(this.propVisit != null){
			this.formData = this.propVisit;
		}
		this.getLoadAll();
	}
}
</script>

<style scoped>
.el-item-textarea{
	width: 100%;
}
.el-item-textarea label{
	display: block;
	padding-top: 20px;
	padding-bottom: 10px;
}
.el-item-textarea #el-item-textarea-label-frist{
	padding-top: 0px;
}
</style>
