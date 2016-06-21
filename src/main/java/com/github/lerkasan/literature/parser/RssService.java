package com.github.lerkasan.literature.parser;

import java.util.List;
import javax.inject.Named;
import com.github.lerkasan.literature.entity.Resource;
import com.rometools.rome.feed.synd.SyndEntry;

@Named
public interface RssService {
	public List<SyndEntry> read(Resource resource);
	public String saveRssNewsToDb(int[] selectedRssNewsIds, List<SyndEntry> rssNews);
}
