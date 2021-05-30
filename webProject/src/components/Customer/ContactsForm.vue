<template>
	<div id="contacts-form-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="姓名:">
				<el-input v-model="formData.name" placeholder="姓名"></el-input>
			</el-form-item>
			<el-form-item id="select-item-sex" label="性别:">
			  <el-select v-model="formData.sex">
			    <el-option label="男" value="男"></el-option>
			    <el-option label="女" value="女"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="部门:">
				<el-input v-model="formData.department" placeholder="职务"></el-input>
			</el-form-item>
			<el-form-item label="职务:">
				<el-input v-model="formData.position" placeholder="职务"></el-input>
			</el-form-item>
			<el-form-item label="年龄:">
				<el-input v-model="formData.age" placeholder="年龄"></el-input>
			</el-form-item>
			<div class="el-item-textarea">
				<div>联系方式：</div>
				<el-form-item label="座机号码:">
					<el-input v-model="formData.landlineTell"></el-input>
				</el-form-item>
				<el-form-item label="手机一:">
					<el-input v-model="formData.phoneOne"></el-input>
				</el-form-item>
				<el-form-item label="手机二:">
					<el-input v-model="formData.phoneTwo"></el-input>
				</el-form-item>
				<el-form-item label="电子邮箱:">
					<el-input v-model="formData.email"></el-input>
				</el-form-item>
				<el-form-item label="QQ:">
					<el-input v-model="formData.qq"></el-input>
				</el-form-item>
				<el-form-item label="微信:">
					<el-input v-model="formData.wchat"></el-input>
				</el-form-item>
				<el-form-item label="其他方式:">
					<el-input v-model="formData.otherTell"></el-input>
				</el-form-item>
			</div>
			
			<div class="el-item-textarea">
				<label id="el-item-textarea-label-frist">兴趣爱好:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.hobby" placeholder="备注"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">社会关系:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.socialRelations" placeholder="备注"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">联络备注:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.note" placeholder="备注"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {
	ccId: null,cid: null,department:'',position: '',name: '',sex: '',age: '',landlineTell: '',
	phoneOne: '',phoneTwo: '',email: '',qq: '',wchat: '',otherTell: '',hobby: '',
	socialRelations: '',note: '',createId: '',updateId: '',updateDate: '',createDate: ''
};
import {GetForParams,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'ContactsForm',
	props:{
		propContactId:Number
	},
	data(){
		return{
			formData
		}
	},
	methods:{
		resetInfo(){
			let formDataTemp = {
				ccId: null,cid: null,department:'',position: '',name: '',sex: '',age: '',landlineTell: '',
				phoneOne: '',phoneTwo: '',email: '',qq: '',wchat: '',otherTell: '',hobby: '',
				socialRelations: '',note: '',createId: '',updateId: '',updateDate: '',createDate: ''
			};
			this.formData = formDataTemp;
		},
		queryFun(ccId){
			this.getContactInfo(ccId);
		},
		//拉取联系人信息
		async getContactInfo(ccId){
			let params = {
				ccId:ccId
			}
			GetForParams('/customer-contacts/one',params).then(res=>{
				this.formData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		if(this.propContactId == null){
			return;
		}
		this.getContactInfo(this.propContactId);
	}
}
</script>


<style lang="scss" scoped>
#contacts-form-main{
	/* width: 100%;
	height: 550px;
	overflow: auto; */
}
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
.el-input-site{
	width: 350px;
	padding: 0 0;
}
#select-item-sex .el-select{
	width: 202px;
}
</style>