DROP DATABASE QuanLyBanHang
CREATE DATABASE QuanLyBanHang
USE QuanLyBanHang

CREATE TABLE LuongCoBan(
	maChucVu VARCHAR(5) NOT NULL PRIMARY KEY,
	tenChucVu NVARCHAR(100) NOT NULL ,	
	luongCoBan MONEY NOT NULL,
)
sp_rename "luongCoBan","LuongCoBan"
CREATE TABLE NhanVien(
maNV VARCHAR(5) NOT NULL PRIMARY KEY,
tenNV NVARCHAR(100) NOT NULL,
maChucVu VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES luongCoBan(maChucVu),
sdtNV VARCHAR(12) NOT NULL,
diaChiNV NVARCHAR(100), 	
)

DROP TABLE LuongHangThang
CREATE TABLE LuongHangThang(
	maNV VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES NhanVien(maNV),
	ngayTinhLuong DATE NOT NULL,
	luongcoban MONEY NOT NULL,
	thuong MONEY NOT NULL DEFAULT 0,
	phuCap MONEY NOT NULL DEFAULT 0,
	buoiVang INT NOT NULL DEFAULT 0,
	thucNhan MONEY NOT NULL,
	PRIMARY KEY (ngayTinhLuong,maNV)	
)

DELETE FROM LuongHangThang


CREATE TABLE NhaCungCap (
	maNCC VARCHAR(6) NOT NULL PRIMARY KEY,
	tenNCC NVARCHAR(100) NOT NULL,
	diaChiNCC NVARCHAR(100),
	sdtNCC VARCHAR(12) NOT NULL, 
)

CREATE TABLE KhachHang(
	maKH VARCHAR(5) NOT NULL PRIMARY KEY,
	tenKH NVARCHAR(100) NOT NULL,
	diachiKH NVARCHAR(100),
	sdtKH VARCHAR(12) NOT NULL,	
)


CREATE TABLE Kho (
	maSP VARCHAR(5) NOT NULL PRIMARY KEY,
	tenSP NVARCHAR(100) NOT NULL,
	soLuong INT DEFAULT 0,
)
ALTER TABLE Kho DROP COLUMN tongXuat

ALTER TABLE Kho ADD soLuong INT DEFAULT 0
CREATE TABLE PhieuNhap (
	maPN VARCHAR(5) NOT NULL PRIMARY KEY,
	ngayNhap DATE NOT NULL,
	maNCC VARCHAR(6) NOT NULL FOREIGN KEY REFERENCES nhaCungCap(maNCC),
)
CREATE TABLE ChiTietPhieuNhap (
	maPN VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES PhieuNhap(maPN),
	maSP VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES Kho(maSP),
	soLuongNhap INT NOT NULL,
	giaNhap MONEY NOT NULL,
	PRIMARY KEY(maPN,maSp),
)


CREATE TABLE PhieuXuat(
	maPX VARCHAR(5) NOT NULL PRIMARY KEY,
	ngayXuat DATE NOT NULL,
	maKH VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES KhachHang(maKH),
	maNVGiaoHang VARCHAR(5) FOREIGN KEY REFERENCES NhanVien(maNV),
	maNVBanHang VARCHAR(5) FOREIGN KEY REFERENCES NhanVien(maNV),
)


CREATE TABLE ChiTietPhieuXuat(
	maPX VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES PhieuXuat(maPX),
	maSP VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES Kho(maSP),
	soLuongXuat INT NOT NULL,
	giaXuat MONEY NOT NULL,
	PRIMARY KEY(maPX,maSP),	
)

CREATE TABLE ChucNangChuongTrinh(
	maCN INT IDENTITY(1,1) PRIMARY KEY,
	tenCN VARCHAR(100),
)

INSERT INTO ChucNangChuongTrinh VALUES 
	('frmMainbtnSignUp'),
	('frmMainbtnEmp'),
	('frmMainbtnWareHouse'),
	('frmMainbtnGRList'),
	('frmMainbtnSupplier'),
	('frmMainbtnOrderList'),
	('frmMainbtnCustomer'),
	('frmMainbtnSalary')



CREATE TABLE NhomChucNang(
	maNhom VARCHAR(4) NOT NULL PRIMARY KEY,
	tenNhom NVARCHAR(100) NOT NULL,	
)
	
	INSERT INTO NhomChucNang VALUES 
	('N001','admin'),
	('N002',N'K? toán'),
	('N003',N'Th? Kho'),
	('N004',N'Bán hàng')
	
CREATE TABLE PhanQuyenNhom(
	maNhom  VARCHAR(4) NOT NULL FOREIGN KEY REFERENCES NhomChucNang(maNhom),
	maCN INT FOREIGN KEY REFERENCES ChucNangChuongTrinh(maCN),
	PRIMARY KEY(maNhom,maCN),
)

	INSERT INTO PhanQuyenNhom VALUES 
	('N001',1),
	('N001',2),
	('N001',3),
	('N001',4),
	('N001',5),
	('N001',6),
	('N001',7),
	('N001',8)
	
	INSERT INTO PhanQuyenNhom VALUES 
	('N002',3),
	('N002',4),
	('N002',5),
	('N002',6),
	('N002',7),
	('N002',8)
	INSERT INTO PhanQuyenNhom VALUES 
	('N003',3),
	('N003',4),
	('N003',5)
	
	INSERT INTO PhanQuyenNhom VALUES 
	('N004',3),
	('N004',6),
	('N004',7)
	
CREATE TABLE TaiKhoan(
	tenTaiKhoan VARCHAR(100) NOT NULL PRIMARY KEY,
	matKhau VARCHAR(100) NOT NULL,
	maNhom  VARCHAR(4) NOT NULL FOREIGN KEY REFERENCES NhomChucNang(maNhom),
)
DELETE FROM TaiKhoan
SELECT * FROM TaiKhoan

CREATE PROCEDURE sp_isExistAdminAccount
@numberAccount int Output AS
SELECT @numberAccount=count(*) FROM TaiKhoan
WHERE maNhom='N001'
DECLARE  @number INT;
EXECUTE sp_isExistAdminAccount @numberAccount= @number output

SELECT @number

CREATE PROCEDURE sp_createAccount
@tentaikhoan VARCHAR(100),@matKhau VARCHAR(100), @maNhom  VARCHAR(4) 
AS
INSERT INTO TaiKhoan VALUES
(@tentaikhoan,@matKhau,@maNhom)




CREATE PROCEDURE sp_getGroupUsers AS 
select * from NhomChucNang

EXECUTE sp_getGroupUsers





CREATE PROCEDURE sp_getGroupFuntions 
@tenCN VARCHAR (4)
AS
SELECT tenCN FROM ChucNangChuongTrinh 
WHERE maCN IN (select maCN from PhanQuyenNhom WHERE maNhom =@tenCN)


EXECUTE sp_getGroupFuntions 'N001'




CREATE PROCEDURE sp_getPosition
AS
SELECT * FROM LuongCoBan

EXEC sp_getPosition

CREATE PROCEDURE sp_addNewPos
	@maChucVu VARCHAR(5),
	@tenChucVu NVARCHAR(100) ,	
	@luongCoBan MONEY
AS 
INSERT INTO luongCoBan
VALUES
(
	@maChucVu,
	@tenChucVu,	
	@luongCoBan
)

 
CREATE PROCEDURE sp_delPos
@maChucVu VARCHAR(5)
AS
DELETE FROM LuongCoBan WHERE maChucVu=@maChucVu


CREATE PROCEDURE sp_editPos
@maChucVu VARCHAR(5),
@tenChucVu NVARCHAR(100) ,	
@luongCoBan MONEY
AS
UPDATE LuongCoBan
SET tenChucVu= @tenChucVu,luongCoBan = @luongCoBan
WHERE maChucVu=@maChucVu






CREATE PROCEDURE sp_getEmp
AS
SELECT * FROM NhanVien AS nv


CREATE PROCEDURE sp_getEmpForPayroll
AS
SELECT nv.maNV,nv.tenNV, lcb.luongCoBan FROM NhanVien AS nv, LuongCoban AS lcb
WHERE nv.maChucVu= lcb.maChucVu


CREATE PROCEDURE sp_getEmpIDAndName
AS
SELECT nv.maNV,nv.tenNV FROM NhanVien AS nv

EXEC sp_getEmpIDAndName 


CREATE PROCEDURE sp_addNewEmp
@maNV VARCHAR(5),
@tenNV NVARCHAR(100) ,
@maChucVu VARCHAR(5),
@sdtNV VARCHAR(12),
@diachiNV NVARCHAR(100)
AS 
INSERT INTO NhanVien

VALUES
(
	@maNV,
	@tenNV,
	@maChucVu,
	@sdtNV,
	@diachiNV
)

CREATE PROC sp_delEmp
@maNV VARCHAR(5)
AS
DELETE FROM NhanVien
WHERE maNV=@maNV





CREATE PROCEDURE sp_getInventory 
AS
SELECT * FROM Kho


CREATE PROCEDURE sp_addNewGoods
	@maSP VARCHAR(5),
	@tenSP NVARCHAR(100)
AS
INSERT INTO Kho
(maSP,tenSP)
VALUES
(@maSP,@tenSP)

CREATE PROCEDURE sp_delGoods @maSP VARCHAR(5)
AS
DELETE FROM Kho WHERE maSP=@maSP






CREATE PROCEDURE sp_getCustomerInfo
AS
SELECT * FROM KhachHang AS kh


CREATE PROCEDURE sp_addNewCustomer
@maKH VARCHAR(5),
@tenKH NVARCHAR(100) ,
@diachiKH NVARCHAR(100),
@sdtKH VARCHAR(12)
AS 
INSERT INTO KhachHang

VALUES
(
	@maKH,
	@tenKh,
	@diachiKH,
	@sdtKH

)

CREATE PROCEDURE sp_delCustomer
@maKH VARCHAR(5)
AS
DELETE FROM KhachHang
WHERE @maKH=maKH




exec  sp_helptext 'sp_getPayroll'

CREATE PROCEDURE sp_getPayroll AS
SELECT nv.maNV,nv.tenNV,lht.ngayTinhLuong ,lht.luongCoBan,lht.thuong,lht.phuCap,lht.buoiVang,lht.thucNhan
FROM NhanVien AS nv, LuongHangThang AS lht
WHERE nv.maNV= lht.maNV 


CREATE PROCEDURE sp_getPayrollTime AS
SELECT distinct ngayTinhLuong FROM Luonghangthang


CREATE PROCEDURE sp_getPayrollWithDate 
@ngayTinhLuong DATE
AS
SELECT nv.maNV, nv.tenNV,lht.ngayTinhLuong ,lht.luongCoBan,lht.thuong,lht.phuCap,lht.buoiVang,lht.thucNhan
FROM NhanVien AS nv, LuongHangThang AS lht
WHERE  nv.maNV= lht.maNV AND lht.ngayTinhLuong = @ngayTinhLuong


CREATE PROCEDURE sp_addPayroll 
@maNV varchar(5) , @ngayTinhLuong  date, @luongcoban MONEY,@thuong MONEY,
@phuCap MONEY, @buoiVang int, @thucNhan MONEY
AS
INSERT INTO LuongHangThang
VALUES
(@maNV , @ngayTinhLuong,@luongcoban, @thuong,@phuCap, @buoiVang , @thucNhan)


CREATE PROCEDURE sp_delPayroll
@maNV varchar(5) , @ngayTinhLuong  date
AS
DELETE FROM LuongHangThang 
WHERE manv=@maNV AND ngayTinhLuong=@ngayTinhLuong





CREATE PROC sp_getGR
AS
SELECT p.maPN, p.ngayNhap,n.tenNCC  FROM PhieuNhap AS p, nhaCungCap AS n
WHERE p.maNCC = n.maNCC

CREATE PROC sp_getGRDetails
@maPN VARCHAR(5)
AS
SELECT c.maSP,k.tenSP,c.soLuongNhap,c.giaNhap FROM ChiTietPhieuNhap AS c, Kho AS k
WHERE c.maSP = k.maSP AND maPN = @maPN

EXEC sp_getGRDetails 'PN001'




DELETE FROM ChiTietPhieuNhap
DELETE FROM PhieuNhap

INSERT INTO PhieuNhap VALUES
 ( 'PN001','2018-01-20','NCC001'),
 ( 'PN002','2018-02-14','NCC001'),
 ( 'PN003','2018-03-20','NCC001'),
 ( 'PN004','2018-04-19','NCC001') 
 
 INSERT INTO ChiTietPhieuNhap VALUES 
 ('PN001','SP001',20,3000 ),
  ('PN001','SP002',10,15000 ),
   ('PN002','SP001',30,2500 ),
   ('PN003','SP002',15,16000)
   
   
   
   
CREATE PROCEDURE sp_getOrder
 AS
 SELECT p.maPX, p.ngayXuat, k.tenKH, p.maNVBanHang,p.maNVGiaoHang
 FROM PhieuXuat AS p,   KhachHang AS k
 WHERE p.maKH=k.maKH
 
 EXECUTE sp_getOrder
  

CREATE PROCEDURE sp_getOrderDetails @maPX NVARCHAR(5)
AS
SELECT c.maSP,k.tenSP,c.soLuongXuat,c.giaXuat
FROM ChiTietPhieuXuat AS c, Kho AS k
WHERE c.maSP=k.maSP AND c.maPX=@maPX

EXECUTE sp_getOrderDetails 'PX001'


DELETE FROM ChiTietPhieuXuat
DELETE FROM PhieuXuat

INSERT INTO PhieuXuat VALUES
 ( 'PX001','2018-01-20','KH001','NV001','NV002'),
 ( 'PX002','2018-02-14','KH001','NV002','NV003'),
 ( 'PX003','2018-03-20','KH001','NV003','NV001'),
 ( 'PX004','2018-04-19','KH001','NV003','NV002')
 
INSERT INTO ChiTietPhieuXuat VALUES
 ('PX001','SP001',20,7000 ),
  ('PX001','SP002',10,25000 ),
   ('PX002','SP001',30,7500 ),
   ('PX003','SP002',15,17000)





CREATE PROCEDURE sp_getSupIDAndName
AS
SELECT n.maNCC, n.tenNCC FROM NhaCungCap AS n

 
 
 sp_helptext 'sp_getsupplier'
 
 
 
 CREATE PROCEDURE sp_addGR
 @maPN VARCHAR(5),
 @ngayNhap DATE,
 @maNCC VARCHAR(6)
 AS
 INSERT INTO PhieuNhap
 VALUES
 (
  @maPN,
  @ngayNhap,
  @maNCC
 )
 
 
 CREATE PROC sp_addGRDetails
 @maPN VARCHAR(5) ,
 @maSP VARCHAR(5),
 @soLuongNhap INT ,
 @giaNhap MONEY
 AS
 INSERT INTO ChiTietPhieuNhap
 VALUES
 (
 	@maPN,
 	@maSP,
 	@soLuongNhap,
 	@giaNhap
 )
 
 
 
 CREATE PROC sp_updateInventory
@maSP VARCHAR(5) ,
@soLuong INT 
AS
UPDATE kho
SET soluong=@soLuong
WHERE maSP=@maSP


CREATE PROCEDURE sp_getCusIDAndName
AS
SELECT k.maKH,k.tenKH FROM KhachHang AS k

EXECUTE sp_getCusIDAndName

CREATE PROCEDURE sp_getEmpIDAndNameWithPosition
@maChucVu VARCHAR(5)
AS
SELECT n.maNV,n.tenNV FROM NhanVien AS n
WHERE maChucVu=@maChucVu

EXECUTE sp_getEmpIDAndNameWithPosition 'CV003'



 CREATE PROCEDURE sp_addOrder
 @maPX VARCHAR(5),
 @ngayXuat DATE,
 @maKH VARCHAR(6),
 @maNVBanHang VARCHAR(5),
 @maNVGiaoHang VARCHAR(5)
 AS
 INSERT INTO PhieuXuat
 VALUES
 (
  @maPX,
  @ngayXuat,
  @maKH,
  @maNVBanHang,
  @maNVGiaoHang
 )
 
 sp_helptext 'sp_addOrder'
 
 CREATE PROC sp_addOrderDetails
 @maPX VARCHAR(5) ,
 @maSP VARCHAR(5),
 @soLuongXuat INT ,
 @giaXuat MONEY
 AS
 INSERT INTO ChiTietPhieuXuat
 VALUES
 (
 	@maPX,
 	@maSP,
 	@soLuongXuat,
 	@giaXuat
 )