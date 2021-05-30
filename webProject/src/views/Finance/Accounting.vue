<template>
	<div id="attendance-main">
		<el-tabs v-model="activeName" @tab-click="handleClick">
		    
			<el-tab-pane v-if="isVoucherEntry(isEntry,isSuperAdmin,isAdmin)" label="凭证录入" name="first">
		    	<VoucherEntry></VoucherEntry>
		    </el-tab-pane>
			<el-tab-pane v-if="isVoucherVerification(isVerification,isSuperAdmin,isAdmin)" label="凭证审核" name="second">
		    	<VoucherVerification></VoucherVerification>
		    </el-tab-pane>
		    <el-tab-pane  v-if="isVoucherRegistration(isRegistration,isSuperAdmin,isAdmin)" label="凭证登账" name="third">
				<VoucherRegistration></VoucherRegistration>
			</el-tab-pane>
			<el-tab-pane label="科目明细账" name="five">
				<SubsidiaryLedger></SubsidiaryLedger>
			</el-tab-pane>
			<el-tab-pane label="总分类账" name="four">
				<GeneralLedger></GeneralLedger>
			</el-tab-pane>
		</el-tabs>
		
	</div>
</template>

<script>

let isSuperAdmin = false;
let isAdmin = false;
let isEntry = false;
let isVerification = false;
let isRegistration = false;
let activeName = 'frist';
import GeneralLedger from '@/components/Finance/Accounting/GeneralLedger.vue';
import SubsidiaryLedger from '@/components/Finance/Accounting/SubsidiaryLedger.vue';
import VoucherEntry from '@/components/Finance/Accounting/VoucherEntry.vue';
import VoucherRegistration from '@/components/Finance/Accounting/VoucherRegistration.vue';
import VoucherVerification from '@/components/Finance/Accounting/VoucherVerification.vue';
export default{
	name:'Accounting',
	components:{
		GeneralLedger,SubsidiaryLedger,VoucherEntry,VoucherRegistration,VoucherVerification
	},
	computed:{
		isVoucherEntry(){
			return function(a,b,c){
				// console.log(a,b,c);
				// this.activeName = 'frist';
				if(a==true || b==true || c==true) return true;
				else return false;
			}
		},
		isVoucherVerification(){
			return function(a,b,c){
				console.log(a,b,c);
				this.activeName = 'second';
				if(a==true || b==true || c==true) return true;
				else return false;
			}
		},
		isVoucherRegistration(){
			return function(a,b,c){
				console.log(a,b,c);
				this.activeName = 'third';
				if(a==true || b==true || c==true) return true;
				else return false;
			}
		}
	},
	data(){
		return{
			isSuperAdmin,isAdmin,isEntry,isVerification,isRegistration,
			activeName
		}
	},
	methods:{
		authorityJudgment(){
			//获取职位
			let pname = this.$store.getters.employee.position.jobTitle;
			console.log(pname);
			if(pname === '超级管理'){
				this.isSuperAdmin = true;
				// this.isAdmin = false;
				// this.isEntry = false;
				// this.isVerification = false;
				// this.isRegistration = false;
			}
			else if(pname == '管理组'){
				// this.isSuperAdmin = false;
				this.isAdmin = true;
				// this.isEntry = false;
				// this.isVerification = false;
				// this.isRegistration = false;
			}else if(pname == '会计组'){
				// this.isSuperAdmin = false;
				// this.isAdmin = false;
				this.isEntry = true;
				// this.isVerification = false;
				// this.isRegistration = false;
			}else if(pname == '审核组'){
				// this.isSuperAdmin = false;
				// this.isAdmin = false;
				// this.isEntry = false;
				this.isVerification = true;
				// this.isRegistration = false;
			}else{
				// this.isSuperAdmin = false;
				// this.isAdmin = false;
				// this.isEntry = false;
				// this.isVerification = false;
				this.isRegistration = true;
			}
			// console.log(this.isSuperAdmin,this.isAdmin,this.isEntry,this.isVerification,this.isRegistration);
		}
	},
	mounted() {
		this.authorityJudgment();
	}
}
</script>

<style scoped>
@import url("../../assets/css/Personal/Attendance.scss");
</style>
