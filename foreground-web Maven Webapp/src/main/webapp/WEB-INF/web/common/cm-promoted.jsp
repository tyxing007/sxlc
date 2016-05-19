<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
			<script type="text/javascript">
				_index.m2 = '<%=request.getParameter("_index_m2")%>';
				_index.m3 = '<%=request.getParameter("_index_m3")%>';
			</script>
            <div class="col-md-3 left_col">
                <div class="left_col scroll-view">
                	<div class="navbar nav_title">
                        <a href="javascript:;" class="site_title">
                        	<i class="fa fa-cog"></i>
                        	<span>产品后台管理</span>
                        </a>
                    </div>
                    <div class="clearfix"></div>
                
                    <!-- sidebar menu -->
                    <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                        <div class="menu_section">
                            <ul class="nav side-menu">
                                <li data-id="1001"><a href="web/promoted/promoted.jsp"><i class="fa fa-home"></i>CPS推广管理</a></li>
                                <li data-id="1002"><a href="web/promoted/recommendedAge.jsp"><i class="fa fa-edit"></i>推荐达人设置</a></li>
                                <li data-id="1003"><a href="web/promoted/pro-applicationAudit.jsp"><i class="fa fa-edit"></i>推荐达人申请审核</a></li>
                                <li data-id="1004"><a href="web/promoted/pro-invitationRecord.jsp"><i class="fa fa-edit"></i>推荐达人邀请记录</a></li>
                                <li data-id="1005"><a href="web/promoted/SEO-promoted.jsp"><i class="fa fa-edit"></i>SEO设置</a></li>
                                <li data-id="1006"><a href="web/promoted/pro-redEnvelope.jsp"><i class="fa fa-edit"></i>红包活动管理</a></li>
                                <li data-id="1007"><a href="web/promoted/pro-envelopeConfiguration.jsp"><i class="fa fa-edit"></i>红包赠送配置</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- /sidebar menu -->

                    <!-- /menu footer buttons -->
                    <div class="sidebar-footer hidden-small">
                        <a data-toggle="tooltip" data-placement="top" title="Settings">
                            <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                        </a>
                        <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                            <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
                        </a>
                        <a data-toggle="tooltip" data-placement="top" title="Lock">
                            <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
                        </a>
                        <a data-toggle="tooltip" data-placement="top" title="Logout">
                            <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                        </a>
                    </div>
                    <!-- /menu footer buttons -->
                </div>
            </div>


		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		