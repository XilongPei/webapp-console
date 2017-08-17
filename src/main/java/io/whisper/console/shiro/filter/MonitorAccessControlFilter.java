package io.whisper.console.shiro.filter;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import io.whisper.console.shiro.service.UserService;
/**
 * isAccessAllowed£º¼´ÊÇ·ñÔÊÐí·ÃÎÊ£¬·µ»Øtrue±íÊ¾ÔÊÐí£»
 * onAccessDenied£º±íÊ¾·ÃÎÊ¾Ü¾øÊ±ÊÇ·ñ×Ô¼º´¦Àí£¬Èç¹û·µ»Øtrue±íÊ¾×Ô¼º²»´¦ÀíÇÒ¼ÌÐøÀ¹½ØÆ÷Á´Ö´ÐÐ£¬·µ»Øfalse±íÊ¾×Ô¼ºÒÑ¾­´¦ÀíÁË£¨±ÈÈçÖØ¶¨Ïòµ½ÁíÒ»¸öÒ³Ãæ£©¡£
 * @author vino007
 *
 */
public class MonitorAccessControlFilter extends AccessControlFilter {
	@Autowired
	private UserService userService;
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object arg2) throws Exception {

		Subject subject=getSubject(request, response);
		String username = (String)subject.getPrincipal();
		if (username == null)
			return false;

		Set<String> permissions = userService.findAllPermissionsByUsername(username);
		for (String permission:permissions) {
			if ("monitor:view".equals(permission)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		//µ¼Ïò404 not found,µ¼ÏòloginÓÉÓÚÓÃ»§ÒÑµÇÂ¼£¬»áµ¼ÖÂloginÎÞÐ§
		WebUtils.issueRedirect(request, response, "/static/404.html");
		return false;
	}



}
