<?php

require_once __DIR__ . '/vendor/autoload.php';

class Handler
{
    /**
     * @var \Illuminate\Filesystem\Filesystem
     */
    protected $filesystem;

    /**
     * @return void
     */
    public function __construct()
    {
        $this->filesystem = new \Illuminate\Filesystem\Filesystem();
    }

    /**
     * @return void
     */
    public function handle()
    {
        $this
            ->git()
            ->pkg()
            ->generate();

        $ruler = $this->ruler();

        foreach ([
                     'vuejs.org',
                     'cn.vuejs.org',
                     'jp.vuejs.org',
                     'ru.vuejs.org',
                     'it.vuejs.org',
                     'kr.vuejs.org',
                     'fr.vuejs.org',
                 ] as $repository) {
            foreach ($this->filesystem->allFiles(__DIR__ . '/' . $repository . '/public') as $fileInfo) {

                switch ($fileInfo->getExtension()) {
                    case 'html':

                        $getContents = $this->filesystem->get($fileInfo->getRealPath());

                        if (preg_match_all('/href="(.+?)"/', $getContents, $matches)) {
                            for ($i = 0; $i < count($matches[0]); $i++) {
                                $value = $matches[1][$i];
                                if ( ! \Illuminate\Support\Str::startsWith($value, $ruler)) {
                                    $relative = $this->replacer($repository, $value, $fileInfo);
                                    $getContents = str_replace('href="' . $value . '"', 'href="' . $relative . '"', $getContents);
                                }
                            }
                        }

                        if (preg_match_all('/src="(.+?)"/', $getContents, $matches)) {
                            for ($i = 0; $i < count($matches[0]); $i++) {
                                $value = $matches[1][$i];
                                if ( ! \Illuminate\Support\Str::startsWith($value, $ruler)) {
                                    $relative = $this->replacer($repository, $value, $fileInfo);
                                    $getContents = str_replace('src="' . $value . '"', 'src="' . $relative . '"', $getContents);
                                }
                            }
                        }

                        $this->filesystem->put($fileInfo->getRealPath(), $getContents);

                        break;
                }
            }

            $this->filesystem->copyDirectory(__DIR__ . '/' . $repository . '/public', __DIR__ . '/app/src/main/assets/' . $repository);
        }
    }

    /**
     * @return $this
     */
    protected function git()
    {
        foreach ([
                     'vuejs.org' => 'https://github.com/vuejs/vuejs.org',
                     'cn.vuejs.org' => 'https://github.com/vuejs/cn.vuejs.org',
                     'jp.vuejs.org' => 'https://github.com/vuejs/jp.vuejs.org',
                     'ru.vuejs.org' => 'https://github.com/translation-gang/ru.vuejs.org',
                     'it.vuejs.org' => 'https://github.com/vuejs/it.vuejs.org',
                     'kr.vuejs.org' => 'https://github.com/vuejs-kr/kr.vuejs.org',
                     'fr.vuejs.org' => 'https://github.com/vuejs-fr/vuejs.org',
                 ] as $directory => $repository) {
            if ( ! $this->filesystem->isDirectory(__DIR__ . '/' . $directory)) {
                exec('git clone ' . $repository . '.git ' . __DIR__ . '/' . $directory, $output, $returnCode);
            }
        }

        return $this;
    }

    /**
     * @return $this
     */
    protected function pkg()
    {
        foreach ([
                     'vuejs.org',
                     'cn.vuejs.org',
                     'jp.vuejs.org',
                     'ru.vuejs.org',
                     'it.vuejs.org',
                     'kr.vuejs.org',
                     'fr.vuejs.org',
                 ] as $repository) {
			exec('cd ' . __DIR__ . '/' . $repository . ' && npm install 2>&1', $output, $returnCode);
        }

        return $this;
    }

    /**
     * @return $this
     */
    protected function generate()
    {
        foreach ([
                     'vuejs.org',
                     'cn.vuejs.org',
                     'jp.vuejs.org',
                     'ru.vuejs.org',
                     'it.vuejs.org',
                     'kr.vuejs.org',
                     'fr.vuejs.org',
                 ] as $repository) {
            exec('cd ' . __DIR__ . '/' . $repository . ' && hexo generate', $output, $returnCode);
        }

        return $this;
    }

    /**
     * @return array
     */
    protected function ruler()
    {
        return [
            'file:///android_asset/',
            '//',
            'http://',
            'https://',
            '_',
            '#',
        ];
    }

    /**
     * @param string $value
     * @param SplFileInfo $fileInfo
     * @return mixed
     */
    protected function replacer($repository, $value, SplFileInfo $fileInfo)
    {

        $android_asset = $value;
        if (preg_match('/(.*)#/', $value, $matches)) {
            $android_asset = $matches[1];
        }

        $directory = __DIR__ . '/' . $repository . '/public';
        if (\Illuminate\Support\Str::startsWith($android_asset, "/")) {
            $specified = $directory . $android_asset;
        } else {
            $specified = realpath($fileInfo->getPath() . '/' . $android_asset);
        }

        if ( ! pathinfo($specified, PATHINFO_EXTENSION)) {
            if ( ! \Illuminate\Support\Str::endsWith($specified, ['\\', '/'])) {
                $specified .= DIRECTORY_SEPARATOR;
            }

            $specified .= 'index.html';
        }

        $absolute = \Illuminate\Support\Str::replaceFirst($directory, 'file:///android_asset/' . $repository, $specified);
        $relative = str_replace(DIRECTORY_SEPARATOR, '/', $absolute);

        return $relative;
    }
}

(new Handler())->handle();
