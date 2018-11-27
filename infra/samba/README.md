# samba

yum install -y samba vim
cd /etc/samba
vim smb.conf

```conf
[global]
    map archive = no

    # 文字コード設定 CentOS
    unix charset = UTF-8

    # 文字コード設定 Win
    dos charset = CP932

    # Windowsのワークグループ名と合わせる
    workgroup = WORKGROUP

    map to guest = Bad User

# /home/vagrantを誰でも読み書きできるようにする
[Vagrant Home]
    path = /home/vagrant
    writable = yes
    force user = vagrant
    force group = vagrant
    guest ok = yes
    guest only = yes
```

```
systemctl start smb
systemctl start nmb
systemctl enable smb
systemctl enable nmb
```

```
getenforce
```

ファイアウォールを無効にする

```
systemctl stop firewalld
systemctl disable firewalld
```

### sambaのログ

/var/log/samba/log.smbd