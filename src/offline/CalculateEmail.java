package offline;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalculateEmail {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String[] pw = parseInput(
				"856ef68d12e6c50f8b4547de50cd13098f38b2a65a74b7c88657df2a7d8c7deeeb9e2fb5ff3be3033d101240656d105880ac17c35f7a51b2948a89aecf25b717c8489c5f91e7ab0069e134d68723f9192498ade2cecb0eb9ac7048ec299dd1bba4d1194b81422e6edb62306995e8aad54f2036b0c68441fbd96873e36c84fbce706a886b308049fe9accb3ba4592ddc5");
		StringBuilder preRes = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("MD5");
		char[] cand = new char[28];
		for (int i = 0; i < 26; i++) {
			cand[i] = (char) ('a' + i);
		}
		cand[26] = '@';
		cand[27] = '.';
		for (int i = 0; i < pw.length; i++) {
			boolean found = false;
			for (int m = 0; m < 28; m++) {
				for (int n = 0; n < 28; n++) {
					preRes.append(cand[m]).append(cand[n]);
					md.update(preRes.toString().getBytes());
					String st1 = byte2Hex(md.digest());
					md.update((preRes.toString() + st1).getBytes());
					String st2 = byte2Hex(md.digest());
					if (st2.equals(pw[i])) {
						found = true;
						break;
					}
					preRes.setLength(preRes.length() - 2);
				}
				if (found) {
					break;
				}
			}
		}
		if (preRes.length() < pw.length * 2) {
			for (int n = 0; n < 28; n++) {
				preRes.append(cand[n]);
				md.update(preRes.toString().getBytes());
				String st1 = byte2Hex(md.digest());
				md.update((preRes.toString() + st1).getBytes());
				String st2 = byte2Hex(md.digest());
				if (st2.equals(pw[pw.length - 1])) {
					break;
				}
				preRes.setLength(preRes.length() - 1);
			}
		}
		System.out.println(preRes.toString());
	}

	private static String[] parseInput(String string) {
		int idx = 0;
		String[] res = new String[string.length() / 32];
		while (idx < string.length()) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 32; i++) {
				sb.append(string.charAt(idx + i));
			}
			res[idx / 32] = sb.toString();
			idx += 32;
		}
		return res;
	}

	private static String byte2Hex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String cur = Integer.toHexString(b[i] & 0XFF);
			if (cur.length() == 1) {
				sb.append("0");
			}
			sb.append(cur);
		}
		return sb.toString();
	}
}
