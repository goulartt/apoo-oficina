package tattool.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Tag implements Serializable{
	 	
		
	    /**
	 * 
	 */
	private static final long serialVersionUID = -8237777512029467082L;

	
		public Tag(String tag) {
			this.tag = tag;
		
		}
		
		public Tag() {
			
		}

		private long id;
	 
	 	
	    private String tag;
	
	    private Set<Art> arts  = new HashSet<Art>(0);

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public Set<Art> getArts() {
			return arts;
		}

		public void setArts(Set<Art> arts) {
			this.arts = arts;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((arts == null) ? 0 : arts.hashCode());
			result = prime * result + (int) (id ^ (id >>> 32));
			result = prime * result + ((tag == null) ? 0 : tag.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tag other = (Tag) obj;
			if (arts == null) {
				if (other.arts != null)
					return false;
			} else if (!arts.equals(other.arts))
				return false;
			if (id != other.id)
				return false;
			if (tag == null) {
				if (other.tag != null)
					return false;
			} else if (!tag.equals(other.tag))
				return false;
			return true;
		}
	    
	    
}
