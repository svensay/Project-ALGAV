package algav.md5;


public class MD5 {

    private static final int R[] =
    {
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };
    private static final int K[] =
    {
        -680876936, -389564586, 606105819, -1044525330, -176418897, 1200080426, -1473231341, -45705983,
        1770035416, -1958414417, -42063, -1990404162, 1804603682, -40341101, -1502002290, 1236535329,
        -165796510, -1069501632, 643717713, -373897302, -701558691, 38016083, -660478335, -405537848,
        568446438, -1019803690, -187363961, 1163531501, -1444681467, -51403784, 1735328473, -1926607734,
        -378558, -2022574463, 1839030562, -35309556, -1530992060, 1272893353, -155497632, -1094730640,
        681279174, -358537222, -722521979, 76029189, -640364487, -421815835, 530742520, -995338651,
        -198630844, 1126891415, -1416354905, -57434055, 1700485571, -1894986606, -1051523, -2054922799,
        1873313359, -30611744, -1560198380, 1309151649, -145523070, -1120210379, 718787259, -343485551
    };

    private static final int H0 = 0x67452301;
    private static final int H1 = 0xEFCDAB89;
    private static final int H2 = 0x98BADCFE;
    private static final int H3 = 0x10325476;

    /**
     * Calcule la somme md5 selon l'algorithme défini dans wikipedia et avec la
     * rfc : http://abcdrfc.free.fr/rfc-vf/rfc1321.html
     *
     * @param data Les données à hacher
     * @return la somme md5 de data
     */
    public static String get(String data)
    {
        byte[] octets = data.getBytes();
        int taille512 = octets.length + 1; // +1 pour le premier octet de bourrage
        while ((8 * taille512) % 512 != 448)
        {
            taille512++;
        }
        taille512 += 8; // 8 octets pour les 64bits de taille à la fin
        assert (taille512 == 512);

        byte[] octetsBourres = new byte[taille512];
        System.arraycopy(octets, 0, octetsBourres, 0, octets.length);
        octetsBourres[octets.length] = (byte) 0b10000000; // premier octet du bourrage 0x80

        // on ajoute le nombre de bits du message (à l'envers car little endian) sur les 8 derniers octets
        long tailleBits = (long) data.length() * 8;
        long nbBlocs = ((data.length() + 8) >>> 6) + 1;
        long tmp = tailleBits;
        for (int i = 0; i < 8; i++)
        {
            octetsBourres[octetsBourres.length - 8 + i] = (byte) tmp;
            tmp >>= 8; // octet suivant
        }

        // déroulement comme indiqué sur le pseudo code de wikipedia
        int a = 0x67452301;
        int b = 0xEFCDAB89;
        int c = 0x98BADCFE;
        int d = 0x10325476;
        for (int bloc = 0; bloc < nbBlocs; bloc++) // 1 bloc = 512 octets
        {
            // on divise chaque bloc en 16 mots binaires (donc 4 octets)
            int w[] = new int[16];
            for (int j = 0; j < 16; j++)
            {
                w[j] = ((int) octetsBourres[bloc * 64 + j * 4] & 0xFF)
                        | ((int) octetsBourres[bloc * 64 + j * 4 + 1] & 0xFF) << 8
                        | ((int) octetsBourres[bloc * 64 + j * 4 + 2] & 0xFF) << 16
                        | ((int) octetsBourres[bloc * 64 + j * 4 + 3] & 0xFF) << 24;
            }
            for (int j = 0; j < 64; j++)
            {
                int f;
                int g = j;
                if (j >= 0 && j <= 15)
                {
                    f = (b & c) | (~b & d);
                }
                else if (j >= 15 && j <= 31)
                {
                    f = (b & d) | (c & ~d);
                    g = (g * 5 + 1) % 16;
                }
                else if (j >= 32 && j <= 47)
                {
                    f = b ^ c ^ d;
                    g = (g * 3 + 5) % 16;
                }
                else
                {
                    f = c ^ (b | ~d);
                    g = (7 * g) % 16;

                }
                int e = Integer.rotateLeft(a + f + w[g] + K[j], R[j]) + b;
                a = d;
                d = c;
                c = b;
                b = e;
            }

            a += H0;
            b += H1;
            c += H2;
            d += H3;
        }

        // on concatene le résultat dans un tableau de 16 octets
        byte[] md5 = new byte[16];
        int tour = -1;
        for (int i = 0; i < 16; i++)
        {
            switch (i / 4)
            {
                case 0:
                    tour = a;
                    break;
                case 1:
                    tour = b;
                    break;
                case 2:
                    tour = c;
                    break;
                case 3:
                    tour = d;
                    break;
            }
            md5[i % 16] = (byte) (tour >> ((i % 16) * 8) & 0xFF);
        }

        return String.format("%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x",
                md5[0] & 0xFF, md5[1] & 0xFF, md5[2] & 0xFF, md5[3] & 0xFF,
                md5[4] & 0xFF, md5[5] & 0xFF, md5[6] & 0xFF, md5[7] & 0xFF,
                md5[8] & 0xFF, md5[9] & 0xFF, md5[10] & 0xFF, md5[11] & 0xFF,
                md5[12] & 0xFF, md5[13] & 0xFF, md5[14] & 0xFF, md5[15] & 0xFF);
    }
}
