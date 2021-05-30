<template>
	<div>
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="开拓渠道:">
			  <el-select v-model="formData.channel" class="el-select-sex" placeholder="开拓渠道">
			    <el-option label="广告投放" value="广告投放"></el-option>
			    <el-option label="熟人介绍" value="熟人介绍"></el-option>
				<el-option label="个人开拓" value="个人开拓"></el-option>
			  </el-select>
				</el-form-item>
			  <el-form-item label="类型:">
			    <el-select v-model="formData.type" class="el-select-sex" placeholder="客户类型">
			      <el-option label="潜在" value="潜在"></el-option>
			      <el-option label="合作" value="合作"></el-option>
				  <el-option label="大客户" value="大客户"></el-option>
			    </el-select>
			  	</el-form-item>
				<el-form-item label="客户单位:">
					<el-input class="el-input-site" v-model="formData.name" placeholder="客户单位名称"></el-input>
				</el-form-item>
			  <el-form-item label="所处地区:">
			  	<el-input class="el-input-site" v-model="formData.region" placeholder="所处地区"></el-input>
			    </el-form-item>
			<el-form-item label="详细地址:">
				<el-input class="el-input-site" v-model="formData.address" placeholder="详细地址"></el-input>
			  </el-form-item>
			<!-- <el-form-item v-show="propsCustomerFormDataCid==null?true:false" label="建立日期:">
				 <el-date-picker
				  v-model="formData.createDate"
				  type="date"
				  placeholder="选择日期">
				</el-date-picker>
			</el-form-item> -->
			<el-form-item label="开户银行:">
				<el-input class="el-input-site" v-model="formData.openBank" placeholder="开户行"></el-input>
			</el-form-item>
			<el-form-item label="银行账号:">
				<el-input class="el-input-site" v-model="formData.bankAccount" placeholder="银行账号"></el-input>
			</el-form-item>
			<div class="el-item-textarea">
				<label >客户介绍:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.note" placeholder="客户介绍"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {
	createId: 0,
	updateId:0,
	name: "",
	address: "",
	openBank: "",
	bankAccount: "",
	type: "",
	region: "",
	note: "",
	channel: "",
	updateDate: "",
	createDate: "",
	cid: null
};
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'CustomerInfo',
	props:{
		propsCustomerFormDataCid:Number 
	},
	data(){
		return{
			formData
		}
	},
	methods:{
		queryFun(cid){
			this.getOneCustomer(cid);
		},
		getOneCustomer(cid){
			let params = {
				cid:cid
			};
			GetForParams("/customer-info/one",params).then(res=>{
				this.formData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
	},
	mounted() {
		// console.log(this.propsCustomerFormDataCid);
		if(this.propsCustomerFormDataCid== null){
			return;
		}
		this.getOneCustomer(this.propsCustomerFormDataCid);
	}
}
</script>

<style lang="scss" scoped>

.el-item-textarea{
	width: 558px;
}
.el-item-textarea label{
	display: block;
	padding-top: 0px;
	padding-bottom: 10px;
}
.el-item-textarea #el-item-textarea-label-frist{
	padding-top: 0px;
}
.el-input-site{
	width: 487px;
	padding: 0 0;
}
</style>
