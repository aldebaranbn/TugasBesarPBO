/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Dell Inspiron 14
 */
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Aplikasi {

    private ArrayList<Dokter> dokter = new ArrayList<>();
    private ArrayList<Ruangan> ruangan = new ArrayList<>();
    final private int maxDokter = 100;
    private int nDokter = 0;
    final private int maxPasien = 1000;
    private int nPasien = 0;

    public boolean insertDokter(ArrayList<Dokter> array) {
        File f = new File("DataDokter.dat");
        ObjectOutputStream dos = null;
        try {
            dos = new ObjectOutputStream(new FileOutputStream(f));
            dos.writeObject(array);
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertRuangan(ArrayList<Ruangan> array) {
        File f = new File("DataRuangan.dat");
        ObjectOutputStream dos = null;
        try {
            dos = new ObjectOutputStream(new FileOutputStream(f));
            dos.writeObject(array);
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
        
    public ArrayList<Dokter> getDokter() {
        File f = new File("DataDokter.dat");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object arrDok = ois.readObject();
            ois.close();
            return (ArrayList<Dokter>) arrDok;
        } catch (EOFException ex) {
            return new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Ruangan> getRuangan() {
        File f = new File("DataRuangan.dat");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            ArrayList<Ruangan> arrRuangan = (ArrayList<Ruangan>) ois.readObject();
            ois.close();
            return arrRuangan;
        } catch (EOFException ex) {
            return new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void addDokter(Dokter d) {
        if (nDokter < maxDokter) {
            dokter.add(d);
            nDokter++;
            insertDokter(dokter);
        } else {
            System.out.println("Dokter sudah penuh");
        }
    }

    public void deletePasien(int noReg) {
        ruangan = getRuangan();
        boolean found = false;
        for (int i = 0; i < ruangan.size(); i++) {
            for (int j = 0; j < ruangan.get(i).getPasienInap().size(); j++) {
                if (ruangan.get(i).getPasienInapByPasienId(noReg).getPasien().getNoRegistrasi() == noReg) {
                    found = true;
                    ruangan.get(i).getPasienInap().remove(j);
                    insertRuangan(ruangan);
                    nPasien--;
                    break;
                } else {
                }
            }
        }
    }

    public PasienInap searchPasien(int noReg) {
        ruangan = getRuangan();
        Ruangan a = null;
        for (Ruangan x : ruangan) {
            for (int i=0 ; i<x.getPasienInap().size();i++){
                if (x.getPasienInap().get(i).getPasien().getNoRegistrasi() == noReg){
                    a = x;
                    break;
                }
            }
        }
        return a.getPasienInapByPasienId(noReg);
    }

    public Dokter getDokter(int nip) {
        dokter = getDokter();
        return dokter.get(nip);
    }

    public void deleteDokter(String nip) {
        dokter = getDokter();
        boolean found = false;
        for (int i = 0; i < nDokter; i++) {
            if (dokter.get(i).getNip().equals(nip)) {
                found = true;
                dokter.remove(i);
                insertDokter(dokter);
                nDokter--;
                break;
            }
        }
    }

    public Dokter searchDokter(String nip) {
        dokter = getDokter();
        Dokter t = null;
        for (Dokter x : dokter) {
            if (x.getNip().equals(nip)) {
                t = x;
            }
        }
        return t;
    }

    public void addRuangan(Ruangan r) {
        ruangan.add(r);
        insertRuangan(ruangan);
    }
    
    public Ruangan getRuanganByNoKamar (int nokamar){
        ruangan = getRuangan();
        Ruangan r = null;
        for (int i = 0; i < ruangan.size(); i++) {
            if (ruangan.get(i).getNoKamar() == nokamar)
            {
                r = ruangan.get(i);
                return r;
            }
        }
        return null;
    }

    public void mainMenu() {
        Scanner s = new Scanner(System.in);
        System.out.println("Sistem Informasi Data Pasien Inap di Rumah Sakit");
        System.out.println("================================================");
        System.out.println("Daftar Menu");
        System.out.println("0. Menambah Ruangan ");
        System.out.println("1. Menambah Dokter ");
        System.out.println("2. Menambah Pasien Inap");
        System.out.println("3. Menghapus Dokter");
        System.out.println("4. Menghapus Pasien Inap");
        System.out.println("5. Mencari Dokter");
        System.out.println("6. Mencari Pasien");
        System.out.println("7. Menampilkan Daftar Dokter");
        System.out.println("8. Menampilkan Daftar Pasien Inap");
        System.out.println("\n");
        System.out.println("Masukan Pilihan Anda : ");
        int n = new Scanner(System.in).nextInt();
        switch (n) {
            case 0:
                System.out.println("Masukkan No Ruangan : ");
                int nor = s.nextInt();
                addRuangan(new Ruangan(nor));
                mainMenu();
                break;
            case 1:
                System.out.println("Menambahkan Dokter");
                System.out.println(" Nama : ");
                String namad = s.next();
                System.out.println(" Jenis Kelamin : ");
                String jkd = s.next();
                System.out.println(" Alamat : ");
                String alamatd = s.next();
                System.out.println(" Bidang Spesialis : ");
                String spesialisd = s.next();
                System.out.println(" NIP : ");
                String nipd = s.next();
                Dokter d = new Dokter(namad, jkd, alamatd, spesialisd, nipd);
                addDokter(d);
                mainMenu();
                break;
            case 2:
                System.out.println("Masukkan nomor kamar : ");
                int no = s.nextInt();                
                Ruangan r = getRuanganByNoKamar(no);
                if (r != null){
                    String nama = null;
                    String alamat = null;
                    String jk = null;
                    int noReg = 0;
                    String jp = null;
                    String di = null;
                    String nip = null;
                    System.out.println("Menambahkan Pasien Inap");
                    System.out.println(" Nama : ");
                    nama = s.next();
                    System.out.println(" Alamat : ");
                    alamat = s.next();
                    System.out.println(" Jenis Kelamin : ");
                    jk = s.next();
                    System.out.println(" No Registrasi : ");
                    noReg = s.nextInt();
                    System.out.println(" NIP Dokter pasien : ");
                    nip = s.next();
                    System.out.println(" Diagnosa Penyakit : ");
                    di = s.next();
                    Dokter dok = searchDokter(nip);
                    if (dok !=null)
                    {
                        r.tambahPasienInap(new Pasien(nama, jk, alamat, noReg), dok, di);
                        insertRuangan(ruangan);
                    }
                    else
                        System.out.println("Dokter tidak ada !!");
                    
                }else {
                    System.out.println("Ruangan tidak ditemukan !!");
                }
                mainMenu();
                break;
            case 3:
                System.out.println("Masukkan NIP Dokter : ");
                String nipDel = s.next();
                deleteDokter(nipDel);
                mainMenu();
                break;
            case 4:
                System.out.println("Masukkan NoReg Pasien : ");
                int noRegDel = s.nextInt();
                deletePasien(noRegDel);
                mainMenu();
                break;
            case 5:
                System.out.println("Masukkan NIP Dokter : ");
                String nipSrch = s.next();
                Dokter q = null;
                q = searchDokter(nipSrch);
                System.out.println("Nama Dokter : " + q.getNama());
                mainMenu();
                break;
            case 6:
                System.out.println("Masukkan NoReg Pasien : ");
                int noRegSrch = s.nextInt();
                PasienInap pa = null;
                pa = searchPasien(noRegSrch);
                searchPasien(noRegSrch);
                System.out.println("Nama Pasien : " + pa.getPasien().getNama());
                mainMenu();
                break;
            case 7:
                System.out.println("Daftar Dokter : ");
                System.out.println("\n");
                for (int i = 0; i < nDokter; i++) {
                    System.out.println(" " + (i + 1) + ". Nama : " + dokter.get(i).getNama());
                    System.out.println("\tNIP : " + dokter.get(i).getNip());
                    System.out.println("\tSpesialis : " + dokter.get(i).getSpesialis());
                    System.out.println("\tAlamat : " + dokter.get(i).getAlamat());
                    System.out.println("\tJenis Kelamin : " + dokter.get(i).getjenisKelamin());
                }
                mainMenu();
                break;
            case 8:
                System.out.println("Daftar Pasien Inap : ");
                System.out.println("\n");
                for (int i = 0; i < ruangan.size(); i++) {
                    for (int j = 0; j < ruangan.get(i).getPasienInap().size(); j++) {
                        System.out.println(" " + (i + 1) + ". \tNama Pasien : " + ruangan.get(i).getPasienInapByIndex(j).getPasien().getNama());
                        System.out.println("\tNo Registrasi : " + ruangan.get(i).getPasienInapByIndex(j).getPasien().getNoRegistrasi());
                        System.out.println("\tNama Dokter : " + ruangan.get(i).getPasienInapByIndex(j).getDokter().getNama());
                        System.out.println("\tDiagnosa Penyakit : "+ruangan.get(i).getPasienInapByIndex(j).getDiagnosa());
                        System.out.println("\tJenis Kelamin : " + ruangan.get(i).getPasienInapByIndex(j).getPasien().getjenisKelamin());
                        System.out.println("\tAlamat : " + ruangan.get(i).getPasienInapByIndex(j).getPasien().getAlamat());
                    }
                }
                mainMenu();
                break;
            default:
                System.out.println("Pilihan yang Dimasukkan Salah");
        }
    }

}
