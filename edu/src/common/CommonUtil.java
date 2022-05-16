package common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class CommonUtil {

	public void fileDownload(HttpServletRequest request
						, HttpServletResponse response
						, String filename, String filepath) {
//		String path = request.getServletContext().getRealPath(filepath);
		// ... edu/upload/2022/01/21/hadfhal_abc.txt
		String path = "d://app" + request.getContextPath() + "/" + filepath;
		File file = new File(path);
		//다운로드할 파일의 마임타입
		String mime = request.getServletContext().getMimeType(filename);
		if( mime==null) mime="application/octet-stream";
		response.setContentType(mime);
		try {
			//한글명의 파일인 경우 한글깨지지 않게 인코딩
			filename = URLEncoder.encode(filename, "utf-8");
			response.setHeader("content-disposition"
							, "attachment; filename=" + filename);
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream in 
				= new BufferedInputStream( new FileInputStream(file) );
			byte[] buf = new byte[1024];
			int read=0;
			while( (read = in.read(buf)) != -1 ) { //2050byte: 1024,1024,2
				out.write(buf, 0, read);
			}
			in.close();
			out.close();
		}catch(Exception e) {
		}
	}
	
	
	public HashMap<String, String> fileUpload(HttpServletRequest request, String category) {
		//서버에서 서비스받는 어플리케이션의 물리적 위치
		//d://Study_Servlet/metadata/.../edu/upload/notice/2022/01/21/abc.txt
		//String app = request.getServletContext().getRealPath("");
		//디스크의 특정위치를 지정하려면
		String app = "d://app" + request.getContextPath() + "/";
		
		String folder = app + "upload/" + category + "/" 
						+ new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		
		File dir = new File(folder);
		if( !dir.exists() ) dir.mkdirs();
		
		HashMap<String, String> fileInfo = new HashMap<String, String>();
		try {
			Collection<Part> files = request.getParts();
			for( Part file : files ) {
				//name에 file 이 있으면 파일태그이고,
				//선택한(첨부된) 파일이 있는 경우
				if( file.getName().contains("file") 
						&& ! file.getSubmittedFileName().isEmpty() ) {
					String filename = file.getSubmittedFileName();
					String uuid = UUID.randomUUID().toString() + "_" + filename;
					//afdhalhr-4lafh-jlter_abc.txt
					file.write( folder + "/" + uuid );
					//d://Study_Servlet/metadata/.../edu/upload/notice/2022/01/21/afdhalhr-4lafh-jlter_abc.txt
					fileInfo.put("filename", filename);
					fileInfo.put("filepath", folder.substring(app.length())+"/"+uuid );
				}
			}
		
		}catch(Exception e) {
		}
		return fileInfo;
	}
	
	
	public String requestAPI( StringBuffer request, String property ) {
		String response="";
		try {
			URL url = new URL(request.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", property);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			response = res.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return response;
	}
	
	public String requestAPI( StringBuffer request ) {
		String response="";
		try {
			URL url = new URL(request.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			response = res.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return response;
	}
}
