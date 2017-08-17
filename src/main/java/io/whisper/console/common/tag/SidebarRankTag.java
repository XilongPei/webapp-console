package io.whisper.console.common.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import io.whisper.console.entity.Constants;
import io.whisper.console.shiro.entity.Resource;
import io.whisper.console.shiro.entity.Role;
import io.whisper.console.shiro.entity.User;

public class SidebarRankTag extends SimpleTagSupport {
	private User currentUser;

	@Override
	public void doTag() throws JspException, IOException {
		List<Resource> menuResources = new ArrayList<Resource>();
		for (Role role:currentUser.getRoles()) {
			Set<Resource> resources = role.getResources();
			for (Resource res : resources) {
				if (res != null && res.getType().equals("menu")) {
					menuResources.add(res);
				}
			}
		}
		Collections.sort(menuResources, new Comparator<Resource>() {
			@Override
			public int compare(Resource o1, Resource o2) {
				//o1>o2�򷵻�����O1=O2����0��С�ڷ��ظ���
				return o1.getPriority().compareTo(o2.getPriority());
			}
		});

		JspWriter out = getJspContext().getOut();

		//temp codes
		out.println("<li><a class='sidebarMenuHref' href='"+ "dashboard/all" +"'><i class='fa  fa-circle-o'></i><span>"+ "Dashboard" +"</span></a></li>");


		for (Resource res : menuResources) {
			if (res.getUrl().equals("druid"))
				out.println("<li><a  href='"+res.getUrl()+"' target='_blank'><i class='fa fa-laptop'></i><span>"+res.getName()+"</span></a></li>");//ֱ����ת�����ҳ��
			else
				out.println("<li><a class='sidebarMenuHref' href='"+res.getUrl()+"'><i class='fa  fa-circle-o'></i><span>"+res.getName()+"</span></a></li>");

		}
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
