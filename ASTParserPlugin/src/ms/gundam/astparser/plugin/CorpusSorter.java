package ms.gundam.astparser.plugin;

import org.eclipse.jdt.ui.text.java.AbstractProposalSorter;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

/**
 * ランキング順にソート
 * 事前に，環境設定-Java-Editor-ContentAssistで切り替える事
 */
public class CorpusSorter extends AbstractProposalSorter {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.text.java.AbstractProposalSorter#compare(org.eclipse.jface.text.contentassist.ICompletionProposal, org.eclipse.jface.text.contentassist.ICompletionProposal)
	 */
	@Override
	public int compare(ICompletionProposal arg0, ICompletionProposal arg1) {
		int l1 = arg0.getDisplayString().length();
		int l2 = arg1.getDisplayString().length();
		if (l1 < l2) {
			return 1;
		} else if (l1 > l2) {
			return -1;
		} else {
			return 0;
		}
	}
}
