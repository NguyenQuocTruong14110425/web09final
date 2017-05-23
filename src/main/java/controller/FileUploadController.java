package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import service.NewsService;
import service.NewsServiceImpl;
import service.UserDocumentService;
import model.FileBucket;
import model.UserDocument;
import util.FileValidator;
import model.NewsConten;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public  class FileUploadController {

	@Autowired
	NewsService newsService;
	
	@Autowired
	UserDocumentService userDocumentService;
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	FileValidator fileValidator;
	
    @InitBinder("fileBucket")
	protected void initBinder(WebDataBinder binder) {
	   binder.setValidator(fileValidator);
	}
    
	
   /* @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

//        model.addAttribute("files", storageService
//                .loadAll()
//                .map(path ->
//                        MvcUriComponentsBuilder
//                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
//                                .build().toString())
//                .collect(Collectors.toList()));

        //return "jsp/test";
    
		return "ckeditor";
    }*/
   @GetMapping("/ckedit")
    public String showEditor(Model model) throws IOException {
	   List<NewsConten> news = newsService.findAllNews();
		model.addAttribute("news", news);
		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
        return "ckeditor";
    }
   
    @GetMapping("/sub")
    public String showHcmute(Model model) throws IOException {
        return "hcmute";
    }   
    
  //home
    @RequestMapping(value={"/","/home"})
    public String showHome(Model model) throws IOException {
    	List<NewsConten> news = newsService.findAllNews();
 		model.addAttribute("news", news);
 		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
        return "pages/menu_home";
    }
    
  
    @RequestMapping(value = {"/managenews" }, method = RequestMethod.GET)
 	public String listNews(ModelMap model) {
		List<NewsConten> news = newsService.findAllNews();
 		model.addAttribute("news", news);
 		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
 		return "index";
 	}
    @RequestMapping(value = { "/newscontent" }, method = RequestMethod.GET)
	public String newsContent(ModelMap model) {
		NewsConten newlist = new NewsConten();
		model.addAttribute("newlist", newlist);
		List<NewsConten> news = newsService.findAllNews();
 		model.addAttribute("news", news);
 		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
 		model.addAttribute("news", news);
		model.addAttribute("edit", false);
		return "ckeditor";
	}
    @RequestMapping(value = { "/news-{Id}" }, method = RequestMethod.GET)
	public String ListNews(@PathVariable String Id, ModelMap model) {
    	Integer idnews = Integer.parseInt(Id);
		NewsConten newlist = newsService.findById(idnews);
		model.addAttribute("newlist", newlist);
		List<NewsConten> news = newsService.findAllNews();
 		model.addAttribute("news", news);
 		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
 		List<UserDocument> documents = userDocumentService.findAllByNewsId(idnews);
		model.addAttribute("documents", documents);
		return "NewsIfo";
	}
	@RequestMapping(value = { "/newscontent" }, method = RequestMethod.POST)
	public String saveNews(NewsConten newspost, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "index";
		}

		newsService.saveNews(newspost);
		
		model.addAttribute("success", " comit successfully");
		List<NewsConten> news = newsService.findAllNews();
 		model.addAttribute("news", news);
 		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
		//return "success";
		return "index";
	}
	@RequestMapping(value = { "/edit-news-{Id}" }, method = RequestMethod.GET)
	public String editNews(@PathVariable String Id, ModelMap model) {
		Integer idnews = Integer.parseInt(Id);
		NewsConten newlist = newsService.findById(idnews);
		model.addAttribute("newlist", newlist);
		
		List<NewsConten> news = newsService.findAllNews();
 		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
 		model.addAttribute("news", news);
		model.addAttribute("edit", true);
		return "ckeditor";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-news-{Id}" }, method = RequestMethod.POST)
	public String updateNews(@Valid NewsConten newspost, BindingResult result,
				ModelMap model, @PathVariable String Id) {
	
			if (result.hasErrors()) {
				return "index";
			}
	
			newsService.updateNews(newspost);
			List<NewsConten> news = newsService.findAllNews();
	 		model.addAttribute("news", news);
	 		List<UserDocument> listdocuments = userDocumentService.findAll();
			model.addAttribute("listdocuments", listdocuments);
			model.addAttribute("success", " updated successfully");
			return "index";
		}
	@RequestMapping(value = { "/delete-news-{Id}" }, method = RequestMethod.GET)
	public String deleteNews(@PathVariable Integer Id,ModelMap model) {
		newsService.deleteNewByID(Id);
		List<NewsConten> news = newsService.findAllNews();
 		model.addAttribute("news", news);
 		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
		model.addAttribute("success", " delete successfully");
		return "index";
	}

/*    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "index";
	}
	*/
	@RequestMapping(value = { "/add-document-{newsId}" }, method = RequestMethod.GET)
	public String addDocuments(@PathVariable int newsId, ModelMap model) {
		NewsConten newslist = newsService.findById(newsId);
		model.addAttribute("newslist", newslist);
		List<NewsConten> news = newsService.findAllNews();
 		model.addAttribute("news", news);
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
			
		List<UserDocument> documents = userDocumentService.findAllByNewsId(newsId);
		model.addAttribute("documents", documents);
		List<UserDocument> listdocuments = userDocumentService.findAll();
		model.addAttribute("listdocuments", listdocuments);
		return "managedocuments";
	}
	

	@RequestMapping(value = { "/download-document-{newsId}-{docId}" }, method = RequestMethod.GET)
	public String downloadDocument(@PathVariable String newsId, @PathVariable int docId, HttpServletResponse response) throws IOException {
		UserDocument document = userDocumentService.findById(docId);
		response.setContentType(document.getType());
        response.setContentLength(document.getContent().length);
        response.setHeader("Content-Disposition","attachment; filename=\"" + document.getName() +"\"");
 
        FileCopyUtils.copy(document.getContent(), response.getOutputStream());
 
 		return "redirect:/add-document-"+newsId;
	}

	@RequestMapping(value = { "/delete-document-{newsId}-{docId}" }, method = RequestMethod.GET)
	public String deleteDocument(@PathVariable int newsId, @PathVariable int docId) {
		userDocumentService.deleteById(docId);
		return "redirect:/add-document-"+newsId;
	}

	@RequestMapping(value = { "/add-document-{newsId}" }, method = RequestMethod.POST)
	public String uploadDocument(@Valid FileBucket fileBucket, BindingResult result, ModelMap model, @PathVariable int newsId) throws IOException{
		
		if (result.hasErrors()) {
			System.out.println("validation errors");
			NewsConten newslist = newsService.findById(newsId);
			model.addAttribute("newslist", newslist);
			List<NewsConten> news = newsService.findAllNews();
	 		model.addAttribute("news", news);

			List<UserDocument> documents = userDocumentService.findAllByNewsId(newsId);
			model.addAttribute("documents", documents);
			
			return "managedocuments";
		} else {
			
			System.out.println("Fetching file");
			
			NewsConten newslist = newsService.findById(newsId);
			model.addAttribute("newslist", newslist);
			List<NewsConten> news = newsService.findAllNews();
	 		model.addAttribute("news", news);

			saveDocument(fileBucket, newslist);

			return "redirect:/add-document-"+newsId;
		}
	}
	
	private void saveDocument(FileBucket fileBucket, NewsConten news) throws IOException{
		
		UserDocument document = new UserDocument();
		
		MultipartFile multipartFile = fileBucket.getFile();
		
		document.setName(multipartFile.getOriginalFilename());
		document.setDescription(fileBucket.getDescription());
		document.setType(multipartFile.getContentType());
		document.setContent(multipartFile.getBytes());
		document.setNews(news);
		userDocumentService.saveDocument(document);
	}
 
}
