package com.aspire.imp.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.LockObtainFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aspire.imp.aop.annotation.Auth;
import com.aspire.imp.common.contanst.ApiResult;
import com.aspire.imp.common.contanst.PostStatusEnum;
import com.aspire.imp.common.contanst.RetCode;
import com.aspire.imp.common.exception.BusinessException;
import com.aspire.imp.common.util.CookieUtils;
import com.aspire.imp.common.util.DateTools;
import com.aspire.imp.common.util.fileupload.FileSystemStorageService;
import com.aspire.imp.lucene.LuceneUtil;
import com.aspire.imp.lucene.LuceneCenter;
import com.aspire.imp.web.entity.Post;
import com.aspire.imp.web.entity.SysUser;
import com.aspire.imp.web.entity.Term;
import com.aspire.imp.web.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

@RestController
@RequestMapping(value = "/rest/post")
public class PostRestController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileSystemStorageService fileSystemStorageService;
	
	@RequestMapping("/page")
	public ApiResult pagePost(
			@RequestParam(required = false) Integer pageNum, 
			@RequestParam(required = false) Integer pageSize) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		List<Post> postList = this.postService.query();		
		PageInfo<Post> pageInfo = new PageInfo<Post>(postList);		
		return new ApiResult(pageInfo);
	}
	
	@RequestMapping("/page/term")
	public ApiResult pagePostByTerm(@RequestParam Integer termId,
			@RequestParam(required = false) Integer pageNum, 
			@RequestParam(required = false) Integer pageSize) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		List<Post> postList = this.postService.queryByTerm(termId);
		PageInfo<Post> pageInfo = new PageInfo<Post>(postList);
		
		return new ApiResult(pageInfo);
	}
	
	@Auth
	@RequestMapping("/page/my")
	public ApiResult pagePostByAuthor(
			@RequestParam(name = "d", required = false) Integer draftFlag,
			@RequestParam(required = false) Integer pageNum, 
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		List<Post> postList = this.postService.queryByAuthor(user.getId(), draftFlag);
		PageInfo<Post> pageInfo = new PageInfo<Post>(postList);
		
		return new ApiResult(pageInfo);
	}
	
	@RequestMapping(value = "/get/{id:\\d+}")
	public ApiResult getPost(@PathVariable Long id) {
		Post post = this.postService.get(id);
		return new ApiResult(post);
	}
	
	@RequestMapping(value = "/list/term")
	public ApiResult listTerm() {
		List<Term> termList = this.postService.queryAllTerm();
		return new ApiResult(termList);
	}
	
	@Auth
	@RequestMapping("/preAdd")
	public ApiResult preAdd(HttpServletResponse response) {
		String randompid = String.valueOf(new Date().getTime()).concat(String.valueOf(new Random().nextInt(10000)));
		CookieUtils.addCookie(response, "pid", randompid);
		return new ApiResult(randompid);
	}
	
	@Auth
	@RequestMapping("/addDraft")
	public ApiResult addDraft(
			@RequestParam String title,
			@RequestParam(required = false) String excerpt,
			@RequestParam String content,
			@RequestParam(required = false) Integer commentStatus,
			@RequestParam(required = false) String headerImage,
			@RequestParam(required = false) Integer termId,
			HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		String tmpPid = CookieUtils.getCookie(request, "pid");
		Post post = this.postService.addPost(user.getId(), title, excerpt, content, commentStatus, headerImage, termId, PostStatusEnum.DRAFT, tmpPid);
		return new ApiResult(post);
	}
	
	@Auth
	@RequestMapping("/addPublish")
	public ApiResult addPost(
			@RequestParam String title,
			@RequestParam(required = false)String excerpt,
			@RequestParam String content,
			@RequestParam(required = false) Integer commentStatus,
			@RequestParam(required = false) String headerImage,
			@RequestParam(required = false) Integer termId,
			HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		String tmpPid = CookieUtils.getCookie(request, "pid");
		Post post = this.postService.addPost(user.getId(), title, excerpt, content, commentStatus, headerImage, termId, PostStatusEnum.PUBLISH, tmpPid);
		return new ApiResult(post);
	}
	
	@Auth
	@RequestMapping("/editDraft")
	public ApiResult editDraft(
			@RequestParam Long id,
			@RequestParam String title,
			@RequestParam(required = false)String excerpt,
			@RequestParam String content,
			@RequestParam(required = false) Integer commentStatus,
			@RequestParam(required = false) String headerImage,
			@RequestParam(required = false) Integer termId) {
		Post post = this.postService.editPost(id, title, excerpt, content, commentStatus, headerImage, termId, PostStatusEnum.DRAFT);
		return new ApiResult(post);
	}
	
	@Auth
	@RequestMapping("/editPublish")
	public ApiResult editPublish(
			@RequestParam Long id,
			@RequestParam String title,
			@RequestParam(required = false)String excerpt,
			@RequestParam String content,
			@RequestParam(required = false) Integer commentStatus,
			@RequestParam(required = false) String headerImage,
			@RequestParam(required = false) Integer termId) {
		Post post = this.postService.editPost(id, title, excerpt, content, commentStatus, headerImage, termId, PostStatusEnum.PUBLISH);
		
		//更新索引库		
		try {
			IndexWriter indexWriter = new IndexWriter(LuceneCenter.directory, LuceneCenter.idxWriterConfig); 
	        Document doc = LuceneUtil.post2Doc(post);  
	        indexWriter.updateDocument(new org.apache.lucene.index.Term("id", String.valueOf(id)), doc);
	        indexWriter.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
				
		return new ApiResult(post);
	}
	
	@Auth
	@RequestMapping("/del")
	public ApiResult delPost(@RequestParam Long id, HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		Post updatePostStatus = this.postService.updatePostStatus(id, user.getId(), PostStatusEnum.DELETED);
		//删除索引库
		if(updatePostStatus != null) {
			org.apache.lucene.index.Term term = new org.apache.lucene.index.Term("id", String.valueOf(id));
			try {
				IndexWriter indexWriter = new IndexWriter(LuceneCenter.directory, LuceneCenter.idxWriterConfig);
		        indexWriter.deleteDocuments(term);  
		        indexWriter.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (LockObtainFailedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		return new ApiResult(RetCode.SUCCESS, "");
	}
	
	@Auth
	@RequestMapping("/publish")
	public ApiResult publishPost(@RequestParam Long id, HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		Post post = this.postService.updatePostStatus(id, user.getId(), PostStatusEnum.PUBLISH);
		
		//新增索引库		
		try {
			IndexWriter indexWriter = new IndexWriter(LuceneCenter.directory, LuceneCenter.idxWriterConfig); 
	        Document document = LuceneUtil.post2Doc(post);  
	        indexWriter.addDocument(document);  
	        indexWriter.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return new ApiResult(post);
	}
	
	@Auth
	@RequestMapping("/thumbsup")
	public ApiResult thumbsupPost(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		Long authorId = user.getId();
		String cookieUpFlag = CookieUtils.getCookie(request, "thumbsup" + id + "-" + authorId);
		if (!Strings.isNullOrEmpty(cookieUpFlag)) {
			throw new BusinessException(RetCode.POST_THUMBSUP_ALREADY, "您已点赞");
		}
		Long count = this.postService.thumbsupPost(id, authorId);
		CookieUtils.addCookie(response, "thumbsup" + id + "-" + authorId, "1", CookieUtils.CookieValidityPeriodEnum.ONE_YEAR);
		return new ApiResult(count);
	}
	
	@Auth
    @PostMapping("/upload")
    public Map<String, String> handleFileUpload(@RequestParam("file") MultipartFile file, 
    		@RequestParam(required = false) String alias,
    		HttpServletRequest request,
    		HttpServletResponse response) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		Long authorId = user.getId();
		String subdir = new StringBuffer().append(authorId)
				.append("/")
				.append(DateTools.getDateTimeString(new Date(), "yyyyMMdd"))
				.toString();
    	fileSystemStorageService.store(subdir, file);
    	
    	String url = "/rest/file/" + subdir + "/" + file.getOriginalFilename();
    	String tmpPid = CookieUtils.getCookie(request, "pid");
    	if (Strings.isNullOrEmpty(tmpPid)) {
    		tmpPid = String.valueOf(new Date().getTime()).concat(String.valueOf(new Random().nextInt(10000)));
    		CookieUtils.addCookie(response, "pid", tmpPid);
    	}
    	this.postService.addSubPost(file, alias, url, authorId, tmpPid);
    	Map<String, String> map = new HashMap<String, String>(1);
    	map.put("link", url);
        return map;
    }
	
	//搜索
	@RequestMapping("/search")
	public ApiResult search(@RequestParam String keyword, @RequestParam int showNum)
			throws IOException, ParseException {
		IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(LuceneCenter.directory));
		QueryParser queryParser = new MultiFieldQueryParser(new String[] { "title", "content" }, LuceneCenter.analyzer);
		// 指定关键词
		Query query = queryParser.parse(keyword);
		TopDocs topDocs = indexSearcher.search(query, showNum);
		
		HashSet<Post> posts = new HashSet<Post>();
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int doc = scoreDoc.doc;
			Document document = indexSearcher.doc(doc);
			Post post = LuceneUtil.doc2Post(document);
			Post post2 = this.postService.get(post.getId());
			posts.add(post2);
		}
		return new ApiResult(posts);

	}
	
	@RequestMapping("/test")
	public String lucentest() {
		List<Post> postList = this.postService.query();
		
		//搜索
		for(Post post:postList){
			try {
				IndexWriter indexWriter = new IndexWriter(LuceneCenter.directory, LuceneCenter.idxWriterConfig); 
		        Document document = LuceneUtil.post2Doc(post);  
		        indexWriter.addDocument(document);  
		        indexWriter.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (LockObtainFailedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}	
		return "索引库创建成功";
	}
	 
	
}
